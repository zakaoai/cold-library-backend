package fr.zakaoai.coldlibrarybackend.infrastructure.implementation


import fr.zakaoai.coldlibrarybackend.infrastructure.JikanApiService
import net.sandrohc.jikan.Jikan
import net.sandrohc.jikan.model.anime.Anime
import net.sandrohc.jikan.model.anime.AnimeEpisode
import net.sandrohc.jikan.model.season.Season
import net.sandrohc.jikan.model.season.SeasonEntry
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@CacheConfig
class JikanAPIServiceImpl(private val jikan: Jikan) : JikanApiService {

    @Cacheable(cacheNames = ["jikanAnimes"], unless = "#result instanceof T(java.lang.Exception)")
    override fun searchAnime(search: String) = jikan.query()
        .anime()
        .search()
        .query(search)
        .execute()
        .cache()

    @Cacheable("jikanAnimes")
    override fun getAnimeById(id: Long): Mono<Anime> = jikan.query().anime().get(id.toInt()).execute()

    @Cacheable("jikanAnimesEpisodes")
    override fun getAnimeEpisodesPage(id: Long): Flux<AnimeEpisode> {
        return jikan.query().anime().episodes(id.toInt()).execute()
    }

    @Cacheable("jikanAnimesEpisodes")
    override fun getAnimeEpisodesFromEpisodeByAnimeIdAndEpisodeNumber(malId: Long, episodeNumber: Int) =
        getAnimeEpisodesPage(malId)
            .filter { anime -> anime.malId >= episodeNumber }

    @Cacheable("jikanAnimesEpisode")
    override fun getAnimeEpisodeByAnimeIdAndEpisodeNumber(malId: Long, episodeNumber: Int): Mono<AnimeEpisode> =
        jikan.query().anime().episode(malId.toInt(), episodeNumber).execute()

    override fun getAnimeBySeason(year: Int, season: Season, page: Int?): Flux<Anime> =
        jikan.query().season()[year, season]
            .page(page)
            .execute()

    @Cacheable("jikanSeasonList")
    override fun getSeason(): Flux<SeasonEntry> = jikan.query().season().list().execute()


}