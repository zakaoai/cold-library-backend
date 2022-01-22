package fr.zakaoai.coldlibrarybackend.torrent.api

import de.kaysubs.tracker.nyaasi.NyaaSiApi
import de.kaysubs.tracker.nyaasi.model.Category
import de.kaysubs.tracker.nyaasi.model.SearchRequest
import fr.zakaoai.coldlibrarybackend.torrent.DTO.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.torrent.repository.TrackedAnimeTorrentRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@CacheConfig(cacheNames = ["torrents"])
class NyaaTorrentService(
    private val nyaaSiApi: NyaaSiApi,
    private val trackedAnimeTorrentRepository: TrackedAnimeTorrentRepository
) {

    @Cacheable
    fun getAnimeSearch(searchTerm: String): SearchRequest {
        return SearchRequest()
            .setCategory(Category.Nyaa.anime)
            .setSortedBy(SearchRequest.Sort.SEEDERS)
            .setOrdering(SearchRequest.Ordering.DESCENDING)
            .setTerm(searchTerm)
    }

    @Cacheable
    fun searchEpisodeTorrent(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeTorrentDTO> {

        return trackedAnimeTorrentRepository.findByMalId(malId)
            .map { trackedAnime -> trackedAnime.searchWords }
            .map { searchWord ->
                when (episodeNumber) {
                    0 -> "VOSTFR $searchWord"
                    else -> "VOSTFR $searchWord $episodeNumber"
                }
            }
            .map(this::getAnimeSearch)
            .map { search -> nyaaSiApi.search(search) }
            .flatMapMany { Flux.fromArray(it) }
            .map { AnimeEpisodeTorrentDTO(it, malId, episodeNumber) }
    }

    fun searchEpisodeTorrentById(torrentId: Int, malId: Int, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO> {
        return Mono.just(nyaaSiApi.getTorrentInfo(torrentId))
            .map { AnimeEpisodeTorrentDTO(it, torrentId, malId, episodeNumber) }
    }
}