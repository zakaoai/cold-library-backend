package fr.zakaoai.coldlibrarybackend.infrastructure.implementation

import de.kaysubs.tracker.nyaasi.NyaaSiApi
import de.kaysubs.tracker.nyaasi.model.Category
import de.kaysubs.tracker.nyaasi.model.SearchRequest
import fr.zakaoai.coldlibrarybackend.infrastructure.NyaaTorrentService
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.TrackedAnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeEpisodeTorrentDTO
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@CacheConfig(cacheNames = ["torrents"])
class NyaaTorrentServiceImpl(
    private val nyaaSiApi: NyaaSiApi,
    private val trackedAnimeTorrentRepository: TrackedAnimeTorrentRepository
) : NyaaTorrentService{

    @Cacheable
    override fun getAnimeSearch(searchTerm: String): SearchRequest {
        return SearchRequest()
            .setCategory(Category.Nyaa.anime)
            .setSortedBy(SearchRequest.Sort.SEEDERS)
            .setOrdering(SearchRequest.Ordering.DESCENDING)
            .setTerm(searchTerm)
    }

    @Cacheable
    override fun searchEpisodeTorrent(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeTorrentDTO> {

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
            .map { it.toAnimeEpisodeTorrentDTO(malId,episodeNumber)  }
    }

    override fun searchEpisodeTorrentById(torrentId: Int, malId: Int, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO> {
        return Mono.just(nyaaSiApi.getTorrentInfo(torrentId))
            .map { it.toAnimeEpisodeTorrentDTO(torrentId, malId, episodeNumber)  }
    }
}