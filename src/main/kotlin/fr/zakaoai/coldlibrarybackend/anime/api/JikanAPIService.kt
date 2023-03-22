package fr.zakaoai.coldlibrarybackend.anime.api

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.fromAnimeEpisode
import net.sandrohc.jikan.Jikan
import net.sandrohc.jikan.model.anime.AnimeEpisode
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
            .map (AnimeDTO::fromAnimeBase)
            .cache()

    @Cacheable("jikanAnimes")
    fun getAnimeById(id: Int): Mono<AnimeDTO> =
            jikan.query().anime().get(id).execute().map { AnimeDTO.fromAnimeBase(it) }

    @Cacheable("jikanAnimesEpisodes")
    fun getAnimeEpisodesPage(id: Int): Flux<AnimeEpisode> {
        return jikan.query().anime().episodes(id).execute()
    }

    @Cacheable("jikanAnimesEpisodes")
    fun getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeDTO> {
        return getAnimeEpisodesPage(malId)
                .filter { anime -> anime.malId > episodeNumber }
                .map { anime -> fromAnimeEpisode(malId, anime) }
    }

}