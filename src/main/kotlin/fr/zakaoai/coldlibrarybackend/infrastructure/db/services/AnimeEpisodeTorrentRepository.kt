package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeEpisodeTorrentRepository : ReactiveCrudRepository<AnimeEpisodeTorrent, Long> {

    fun findByMalId(malId: Int?): Flux<AnimeEpisodeTorrent>

    fun findByMalIdAndEpisodeNumber(malId: Int?, episodeNumber: Int?): Mono<AnimeEpisodeTorrent>

    fun deleteByMalIdAndEpisodeNumber(malId: Int?, episodeNumber: Int?): Mono<Void>

    fun deleteByMalId(malId: Int?): Mono<Void>
}