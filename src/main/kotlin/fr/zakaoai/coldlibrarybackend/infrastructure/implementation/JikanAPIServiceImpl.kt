package fr.zakaoai.coldlibrarybackend.infrastructure.implementation


import fr.zakaoai.coldlibrarybackend.infrastructure.JikanAPIService
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeDTO
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeEpisode
import net.sandrohc.jikan.Jikan
import net.sandrohc.jikan.model.anime.Anime
import net.sandrohc.jikan.model.anime.AnimeEpisode
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@CacheConfig
class JikanAPIServiceImpl(private val jikan: Jikan) : JikanAPIService {

    @Cacheable(cacheNames = ["jikanAnimes"], unless = "#result instanceof T(java.lang.Exception)")
    override fun searchAnime(search: String): Flux<AnimeDTO> = jikan.query()
        .anime()
        .search()
        .query(search)
        .execute()
        .map(Anime::toAnimeDTO)
        .cache()

    @Cacheable("jikanAnimes")
    override fun getAnimeById(id: Int): Mono<AnimeDTO> =
        jikan.query().anime().get(id).execute().map(Anime::toAnimeDTO)

    @Cacheable("jikanAnimesEpisodes")
    override fun getAnimeEpisodesPage(id: Int): Flux<AnimeEpisode> {
        return jikan.query().anime().episodes(id).execute()
    }

    @Cacheable("jikanAnimesEpisodes")
    override fun getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeDTO> {
        return getAnimeEpisodesPage(malId)
            .filter { anime -> anime.malId > episodeNumber }
            .map { anime -> anime.toAnimeEpisode(malId) }
    }

}