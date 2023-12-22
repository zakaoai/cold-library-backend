package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeEpisodeRepository

import fr.zakaoai.coldlibrarybackend.infrastructure.JikanAPIService
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Anime
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisode
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toFlux

@Service
class EpisodeService(
    private val animeEpisodeRepository: AnimeEpisodeRepository,
    private val animeRepository: AnimeRepository,
    private val jikanAPIService: JikanAPIService
) {

    fun findEpisodeAnimeByAnimeId(malId: Int): Flux<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {
        return animeEpisodeRepository.getWithMalId(malId.toString())
           .map(AnimeEpisode::toAnimeEpisodeDTO)
    }

    fun findEpisodeAnimeByAnimeIdAndEpisodeNumber(
        malId: Int,
        episodeNumber: Int
    ): Mono<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {
        return animeEpisodeRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .map(AnimeEpisode::toAnimeEpisodeDTO)
    }

    // Old function that was used to retrieve anime episode ( MAL don't reference all episodes )
    fun searchAnimeEpisodeFromEpisodeAndSave(
        malId: Int,
        fromEpisode: Int
    ): Flux<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {
        return jikanAPIService.getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId, fromEpisode)
            .flatMap(this::saveEpisode)
    }

    fun searchEpisodesByAnimeId(malId: Int): Flux<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {

        return findEpisodeAnimeByAnimeId(malId)
            .concatWith(saveAllMissingEpisodeFromAnimeMalId(malId))
            .sort(compareBy(fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO::episodeNumber))
    }

    fun searchEpisodeByAnimeIdAndEpisodeNumber(
        malId: Int,
        episodeNumber: Int
    ): Mono<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {
        return findEpisodeAnimeByAnimeIdAndEpisodeNumber(malId, episodeNumber)
            .switchIfEmpty {
                saveMissingEpisodeFromMalIdAndEpisodeNumber(malId, episodeNumber)
            }
    }

    fun numberToRangeOfEpisode(
        lastAvaibleEspidode: Int,
        malId: Int
    ): Flux<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {
        return (1..lastAvaibleEspidode).map { episodeNumber ->
            fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO(
                malId,
                "Episode $episodeNumber",
                episodeNumber
            )
        }.toFlux()
    }

    fun getAllEpisodeFromAnimeMalId(malId: Int): Flux<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {

        return animeRepository.findByMalId(malId)
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST)))
            .map(Anime::lastAvaibleEpisode)
            .flatMapMany { lastAvaibleEpisode ->
                numberToRangeOfEpisode((lastAvaibleEpisode ?: 1), malId)
            }
    }

    fun saveMissingEpisodeFromMalIdAndEpisodeNumber(
        malId: Int,
        episodeNumber: Int
    ): Mono<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {
        return animeRepository.findByMalId(malId)
            .filter { episodeNumber <= (it.lastAvaibleEpisode ?: 1) }
            .map {
                fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO(
                    malId,
                    "Episode $episodeNumber",
                    episodeNumber
                )
            }
            .flatMap(this::saveEpisode)
    }

    fun saveAllMissingEpisodeFromAnimeMalId(malId: Int): Flux<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {
        return animeEpisodeRepository.getWithMalId(malId.toString())
            .map(AnimeEpisode::episodeNumber)
            .collectList()
            .flatMapMany { listEpNumber ->
                getAllEpisodeFromAnimeMalId(malId)
                    .filter { ep -> !listEpNumber.contains(ep.episodeNumber) }
            }
            .flatMap(this::saveEpisode)
    }

    fun removeEpisodesByAnimeId(malId: Int): Mono<Void> {
        return animeEpisodeRepository.deleteByMalId(malId)
    }

    fun saveEpisode(episode: fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO): Mono<fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO> {
        return Mono.just(episode)
            .map(fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO::toModel)
            .flatMap(animeEpisodeRepository::save)
            .map(AnimeEpisode::toAnimeEpisodeDTO)
    }

}