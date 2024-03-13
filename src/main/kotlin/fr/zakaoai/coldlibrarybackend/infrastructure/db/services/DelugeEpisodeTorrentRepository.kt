package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.DelugeEpisodeTorrent
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface DelugeEpisodeTorrentRepository : ReactiveCrudRepository<DelugeEpisodeTorrent, Long> {
    fun findByTorrentId(torrentId: Int): Mono<DelugeEpisodeTorrent>
}