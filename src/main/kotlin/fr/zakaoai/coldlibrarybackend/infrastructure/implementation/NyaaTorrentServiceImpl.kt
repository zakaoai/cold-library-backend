package fr.zakaoai.coldlibrarybackend.infrastructure.implementation

import de.kaysubs.tracker.nyaasi.NyaaSiApi
import de.kaysubs.tracker.nyaasi.model.Category
import de.kaysubs.tracker.nyaasi.model.SearchRequest
import de.kaysubs.tracker.nyaasi.model.TorrentPreview
import fr.zakaoai.coldlibrarybackend.infrastructure.NyaaTorrentService
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
//    private val trackedAnimeTorrentRepository: TrackedAnimeTorrentRepository
) : NyaaTorrentService{

    val filterSearchWord = listOf("x265", "1080p" , "720p", "10 bits", "5.1", "x264", "1920", "1080")

    @Cacheable
    override fun getAnimeSearch(searchTerm: String): SearchRequest {
        return SearchRequest()
            .setCategory(Category.Nyaa.anime)
            .setSortedBy(SearchRequest.Sort.SEEDERS)
            .setOrdering(SearchRequest.Ordering.DESCENDING)
            .setTerm(searchTerm)
    }

    fun filterSearchRemovingWords( episodeNumber: Int)  = { torrentPreview: TorrentPreview ->
            var copyTitle = torrentPreview.title
            filterSearchWord.forEach { copyTitle = copyTitle.replace(it, "") }
             copyTitle.contains(episodeNumber.toString())
    }

//    @Cacheable
//    override fun searchEpisodeTorrent(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeTorrentDTO> {
//
//        return trackedAnimeTorrentRepository.findByMalId(malId)
//            .map { trackedAnime -> trackedAnime.searchWords }
//            .map { searchWord ->
//                when (episodeNumber) {
//                    0 -> "VOSTFR $searchWord"
//                    else -> "VOSTFR $searchWord $episodeNumber"
//                }
//            }
//            .map(this::getAnimeSearch)
//            .map { search -> nyaaSiApi.search(search) }
//            .map{ it.filter(filterSearchRemovingWords(episodeNumber))}
//            .flatMapMany { Flux.fromIterable(it) }
//            .map { it.toAnimeEpisodeTorrentDTO(malId,episodeNumber)  }
//    }

    override fun searchEpisodeTorrentById(torrentId: Int, malId: Int, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO> {
        return Mono.just(nyaaSiApi.getTorrentInfo(torrentId))
            .map { it.toAnimeEpisodeTorrentDTO(torrentId, malId, episodeNumber)  }
    }
}