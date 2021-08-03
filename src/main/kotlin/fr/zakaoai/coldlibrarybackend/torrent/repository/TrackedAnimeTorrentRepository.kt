package fr.zakaoai.coldlibrarybackend.torrent.repository

import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.TrackedAnimeTorrent
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface TrackedAnimeTorrentRepository : ReactiveCrudRepository<TrackedAnimeTorrent, Long> {

    fun findByMalId(malId: Int?): Mono<TrackedAnimeTorrent>

    fun deleteByMalId(malId: Int?): Mono<Void>
}