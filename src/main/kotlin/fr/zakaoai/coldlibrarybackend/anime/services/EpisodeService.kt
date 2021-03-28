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
import reactor.kotlin.core.publisher.toMono

@Service
class EpisodeService(
    private val animeEpisodeRepository: AnimeEpisodeRepository,
    private val animeRepository: AnimeRepository,
    private val jikanAPIService: JikanAPIService
) {

    fun findEpisodeAnimeByAnimeId(malId: Int): Flux<AnimeEpisodeDTO> {
        return animeEpisodeRepository.findByMalId(malId).map(AnimeEpisode::toAnimeEpisodeDTO)
    }


    fun searchEpisodesByAnimeId(malId: Int): Flux<AnimeEpisodeDTO> {

        return animeRepository.findByMalId(malId).flatMap { anime ->
            if (anime.lastAvaibleEpisode === null)
                throw ResponseStatusException(HttpStatus.BAD_REQUEST)
            anime.lastAvaibleEpisode.toMono()
        }.flatMap { lastAvaibleEpisode ->
            findEpisodeAnimeByAnimeId(malId)
                .collectList()
                .flatMap { animes ->
                    animes.sortBy { it.episodeNumber }
                    val lastEpisodeTracked: Int = Math.max(lastAvaibleEpisode,  0.takeUnless { !animes.isEmpty() } ?: animes.last()?.episodeNumber ?: 0)
                    jikanAPIService.getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId, lastEpisodeTracked)
                        .map(AnimeEpisodeDTO::toModel)
                        .flatMap(animeEpisodeRepository::save)
                        .map(AnimeEpisode::toAnimeEpisodeDTO)
                        .concatWith(animes.toFlux()).collectList()
                }
        }.flatMapMany { it -> Flux.fromIterable(it) }
    }

    fun removeEpisodesByAnimeId(malId: Int): Mono<Void> {
       return animeEpisodeRepository.deleteByMalId(malId)
    }

    fun removeEpisodeByAnimeIdFromEpisodeNumber(malId: Int, episodeNumber: Int): Mono<Void> {
        return animeEpisodeRepository.findByMalId(malId).filter{ it.episodeNumber < episodeNumber}.flatMap{ it -> animeEpisodeRepository.delete(it)}.toMono()
    }

    fun removeOldEpisodeByAnimeId(malId: Int): Mono<Void> {
      return  animeRepository.findByMalId(malId).map(Anime::lastAvaibleEpisode).flatMap{ lastAvaibleEpisode -> lastAvaibleEpisode?.let{ i -> removeEpisodeByAnimeIdFromEpisodeNumber(malId,i)  }}
    }


}