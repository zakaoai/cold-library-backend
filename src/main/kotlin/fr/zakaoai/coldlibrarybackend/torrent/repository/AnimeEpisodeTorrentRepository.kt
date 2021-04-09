package fr.zakaoai.coldlibrarybackend.torrent.repository

import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.AnimeEpisodeTorrent
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface AnimeEpisodeTorrentRepository : ReactiveCrudRepository<AnimeEpisodeTorrent, Long> {

    fun findByMalId(malId: Int?): AnimeEpisodeTorrent

    fun deleteByMalIdAndEpisodeNumber(malId : Int?,episodeNumber: Int?): Mono<Void>
}