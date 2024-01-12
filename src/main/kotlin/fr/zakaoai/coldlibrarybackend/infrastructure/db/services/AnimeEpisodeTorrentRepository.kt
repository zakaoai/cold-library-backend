package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeEpisodeTorrentRepository : ReactiveCrudRepository<AnimeEpisodeTorrent, Long> {

    fun findByMalId(malId: Long): Flux<AnimeEpisodeTorrent>

    fun deleteByMalId(malId: Long): Mono<Void>
}