package fr.zakaoai.coldlibrarybackend.anime.services

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.toModel
import fr.zakaoai.coldlibrarybackend.anime.api.JikanAPIService
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeEpisodeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.AnimeEpisode
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
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
            animeEpisodeRepository.findByMalId(malId)
                .collectList()
                .flatMap { animes ->
                    animes.sortBy { it.episodeNumber }
                    val lastEpisodeTracked: Int = Math.max(lastAvaibleEpisode, animes.last()?.episodeNumber ?: 0)
                    jikanAPIService.getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId, lastEpisodeTracked)
                        .map(AnimeEpisodeDTO::toModel)
                        .flatMap(animeEpisodeRepository::save)
                        .map(AnimeEpisode::toAnimeEpisodeDTO)
                        .concatWith(animes.map(AnimeEpisode::toAnimeEpisodeDTO).toFlux()).collectList()
                }
        }.flatMapMany { it -> Flux.fromIterable(it) }
    }
}