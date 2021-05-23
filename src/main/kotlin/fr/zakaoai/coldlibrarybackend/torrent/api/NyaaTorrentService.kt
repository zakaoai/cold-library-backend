package fr.zakaoai.coldlibrarybackend.torrent.api

import de.kaysubs.tracker.nyaasi.NyaaSiApi
import de.kaysubs.tracker.nyaasi.model.*
import fr.zakaoai.coldlibrarybackend.torrent.DTO.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.torrent.repository.TrackedAnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.AnimeEpisodeTorrent
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.util.*

@Service
@CacheConfig(cacheNames = ["torrents"])
class NyaaTorrentService(private val nyaaSiApi: NyaaSiApi,
                         private val trackedAnimeTorrentRepository: TrackedAnimeTorrentRepository) {

    @Cacheable
    fun getAnimeSearch(searchTerm : String) : SearchRequest {
        return SearchRequest()
            .setCategory(Category.Nyaa.anime)
            .setSortedBy(SearchRequest.Sort.SEEDERS)
            .setOrdering(SearchRequest.Ordering.DESCENDING)
            .setTerm(searchTerm)
    }

    @Cacheable
    fun searchEpisodeTorrent(malId : Int, episodeNumber: Int): Flux<AnimeEpisodeTorrentDTO> {

        return trackedAnimeTorrentRepository.findByMalId(malId)
            .map{trackedAnime -> trackedAnime.searchWords}
            .map{searchWord -> "VOSTFR $searchWord $episodeNumber"}
            .map(this::getAnimeSearch)
            .map { search -> nyaaSiApi.search(search)}
            .flatMapMany{ Flux.fromArray(it)}
            .map { AnimeEpisodeTorrentDTO(it,malId,episodeNumber) }
    }
}