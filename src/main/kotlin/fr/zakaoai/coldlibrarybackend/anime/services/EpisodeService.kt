package fr.zakaoai.coldlibrarybackend.anime.services

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.api.JikanAPIService
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeEpisodeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.AnimeEpisode
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class EpisodeService(
        private val animeEpisodeRepository: AnimeEpisodeRepository,
        private val animeRepository: AnimeRepository,
        private val jikanAPIService: JikanAPIService
) {

    fun findEpisodeAnimeByAnimeId(malId: Int): Flux<AnimeEpisodeDTO> {
        return animeEpisodeRepository.findByMalId(malId).map(AnimeEpisode::toAnimeEpisodeDTO)
    }

    fun searchAnimeEpisodeFromEpisodeAndSave(malId: Int, fromEpisode: Int): Flux<AnimeEpisodeDTO> {
        return jikanAPIService.getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId, fromEpisode)
                .map(AnimeEpisodeDTO::toModel)
                .flatMap(animeEpisodeRepository::save)
                .map(AnimeEpisode::toAnimeEpisodeDTO)
    }

    fun searchEpisodesByAnimeId(malId: Int): Flux<AnimeEpisodeDTO> {

        return findEpisodeAnimeByAnimeId(malId)
                .concatWith(saveAllMissingEpisodeFromAnimeMalId(malId))
                .sort(compareBy(AnimeEpisodeDTO::episodeNumber))
    }

    fun numberToRangeOfEpisode(lastAvaibleEspidode: Int, malId: Int): Flux<AnimeEpisodeDTO> {
        return (1..lastAvaibleEspidode).map { episodeNumber ->
            AnimeEpisodeDTO(malId, "Episode $episodeNumber", episodeNumber)
        }.toFlux()
    }

    fun getAllEpisodeFromAnimeMalId(malId: Int): Flux<AnimeEpisodeDTO> {

        return animeRepository.findByMalId(malId)
                .map(Anime::lastAvaibleEpisode)
                .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST)))
                .flatMapMany { lastAvaibleEpisode ->
                    numberToRangeOfEpisode((lastAvaibleEpisode ?: 1), malId)
                }

    }

    fun saveAllMissingEpisodeFromAnimeMalId(malId: Int): Flux<AnimeEpisodeDTO> {
        return findEpisodeAnimeByAnimeId(malId)
                .map(AnimeEpisodeDTO::episodeNumber)
                .collectList()
                .flatMapMany { listEpNumber ->
                    getAllEpisodeFromAnimeMalId(malId)
                            .filter { ep -> !listEpNumber.contains(ep.episodeNumber) }
                }
                .map(AnimeEpisodeDTO::toModel)
                .flatMap(animeEpisodeRepository::save)
                .map(AnimeEpisode::toAnimeEpisodeDTO)
    }

    fun removeEpisodesByAnimeId(malId: Int): Mono<Void> {
        return animeEpisodeRepository.deleteByMalId(malId)
    }

}