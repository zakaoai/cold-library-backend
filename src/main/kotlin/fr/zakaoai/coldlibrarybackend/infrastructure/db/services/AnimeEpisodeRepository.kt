package fr.zakaoai.coldlibrarybackend.anime.repository

import fr.zakaoai.coldlibrarybackend.anime.repository.entity.AnimeEpisode
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeEpisodeRepository : ReactiveCrudRepository<AnimeEpisode, Long> {

    fun findByMalId(malId: Int): Flux<AnimeEpisode>
    fun findByMalIdAndEpisodeNumber(malId: Int, EpisodeNumber: Int): Mono<AnimeEpisode>

    fun deleteByMalId(malId: Int): Mono<Void>
}