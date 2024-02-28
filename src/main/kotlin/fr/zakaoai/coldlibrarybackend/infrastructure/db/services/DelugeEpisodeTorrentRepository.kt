package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.DelugeEpisodeTorrent
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface DelugeEpisodeTorrentRepository : ReactiveCrudRepository<DelugeEpisodeTorrent, Long> {
    fun findByIdAnimeEpisodeTorrent(idAnimeEpisodeTorrent: Long): Mono<DelugeEpisodeTorrent>
    fun findByTorrentId(torrentId: Int): Mono<DelugeEpisodeTorrent>
}