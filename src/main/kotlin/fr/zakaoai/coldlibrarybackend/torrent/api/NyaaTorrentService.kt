package fr.zakaoai.coldlibrarybackend.torrent.api

import de.kaysubs.tracker.nyaasi.NyaaSiApi
import de.kaysubs.tracker.nyaasi.model.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@Service
class NyaaTorrentService(private val nyaaSiApi: NyaaSiApi) {

    fun searchTorrent(animeEpisode : String): Flux<TorrentPreview> {

        val search = SearchRequest()
            .setCategory(Category.Nyaa.anime)
            .setSortedBy(SearchRequest.Sort.SEEDERS)
            .setOrdering(SearchRequest.Ordering.DESCENDING)
            .setTerm("VOSTFR $animeEpisode")

        return nyaaSiApi.search(search).toFlux()
    }
}