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

    @Cacheable(cacheNames = ["jikanAnimes"] ,  unless = "#result instanceof T(java.lang.Exception)"  )
    fun searchAnime(search: String): Flux<AnimeDTO> = jikan.query()
            .anime()
            .search()
            .query(search)
            .execute()
            .map { AnimeDTO.fromAnimeBase(it) }
            .cache()

    @Cacheable("jikanAnimes")
    fun getAnimeById(id: Int): Mono<AnimeDTO> =
            jikan.query().anime().get(id).execute().map { AnimeDTO.fromAnimeBase(it) }

    @Cacheable("jikanAnimesEpisodes")
    fun getAnimeEpisodesPage(id: Int, page: Int = 1): Mono<AnimeEpisodes> {
        return jikan.query().anime().episodes(id, page).execute()
    }

    @Cacheable("jikanAnimesEpisodes")
    fun getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeDTO> {
        val initialPage: Int = Math.floorDiv(episodeNumber, 100) + 1
        var currentPage: Int = initialPage
        return getAnimeEpisodesPage(malId, initialPage).expand { response ->
            if (currentPage == response.lastPage) Mono.empty()
            else {
                currentPage++
                getAnimeEpisodesPage(malId, currentPage)
            }
        }.map(AnimeEpisodes::episodes)
                .flatMap { Flux.fromIterable(it) }
                .filter { anime -> anime.episodeId > episodeNumber }
                .map { anime -> fromAnimeEpisode(malId, anime) }
    }

}