package fr.zakaoai.coldlibrarybackend.infrastructure

import de.kaysubs.tracker.nyaasi.model.SearchRequest
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface NyaaTorrentService {


    fun getAnimeSearch(searchTerm: String): SearchRequest


    fun searchEpisodeTorrent(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeTorrentDTO>

    fun searchEpisodeTorrentById(torrentId: Int, malId: Int, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO>
}