package fr.zakaoai.coldlibrarybackend.infrastructure

import de.kaysubs.tracker.nyaasi.model.SearchRequest
import de.kaysubs.tracker.nyaasi.model.TorrentInfo
import de.kaysubs.tracker.nyaasi.model.TorrentPreview
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface NyaaTorrentService {

    fun getAnimeSearch(searchTerm: String): SearchRequest
    fun searchEpisodeTorrent(
        malId: Long,
        episodeNumber: Int,
        searchWord: String,
    ): Flux<TorrentPreview>

    fun searchEpisodeTorrentById(torrentId: Int): Mono<TorrentInfo>
}