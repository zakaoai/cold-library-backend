package fr.zakaoai.coldlibrarybackend.infrastructure.db.services


import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisode
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeEpisodeRepository : ReactiveCrudRepository<AnimeEpisode, Long> {

    fun findByMalId(malId: Long): Flux<AnimeEpisode>

    fun findByMalIdAndEpisodeNumber(malId: Long, episodeNumber: Int): Mono<AnimeEpisode>

    fun deleteByMalId(malId: Long): Mono<Void>

}