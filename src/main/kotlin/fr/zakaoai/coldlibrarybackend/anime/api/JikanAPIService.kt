package fr.zakaoai.coldlibrarybackend.anime.api

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.fromAnimeEpisode
import net.sandrohc.jikan.Jikan
import net.sandrohc.jikan.model.anime.AnimeEpisodes
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@CacheConfig
class JikanAPIService(private val jikan: Jikan) {

    @Cacheable("jikanAnimes")
    fun searchAnime(search: String): Flux<AnimeDTO> = jikan.query()
        .anime()
        .search()
        .query(search)
        .execute()
        .cache()
        .map { AnimeDTO.fromAnimeBase(it) }

    @Cacheable("jikanAnimes")

    @Cacheable("jikanAnimesEpisodes")

    fun getAnimeEpisode(id: Int, page: Int = 1): Mono<AnimeEpisodes> {
        return jikan.query().anime().episodes(id, page).execute()
    }

    @Cacheable("jikanAnimesEpisodes")
    fun getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeDTO> {
        val initialPage: Int = Math.floorDiv(episodeNumber, 100) + 1
        var currentPage: Int = initialPage
        return getAnimeEpisode(malId, initialPage).expand { response ->
            if (currentPage == response.lastPage) Mono.empty()
            else {
                currentPage++
                getAnimeEpisode(malId, currentPage)
            }
        }.flatMap { response -> Flux.fromIterable(response.episodes) }
            .filter{ anime -> anime.episodeId > episodeNumber}
            .map { anime -> fromAnimeEpisode(malId, anime) }
    }

}