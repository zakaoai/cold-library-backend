package fr.zakaoai.coldlibrarybackend.anime.api

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.fromAnimeBase
import fr.zakaoai.coldlibrarybackend.anime.DTO.fromAnimeEpisode
import net.sandrohc.jikan.Jikan
import net.sandrohc.jikan.model.anime.AnimeEpisodes
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class JikanAPIService(private val jikan: Jikan) {

    fun searchAnime(search: String): Flux<AnimeDTO> = jikan.query()
        .anime()
        .search()
        .query(search)
        .execute()
        .cache()
        .map { it -> fromAnimeBase(it) }


    fun getAnimeById(id: Int): Mono<AnimeDTO> = jikan.query().anime().get(id).execute().map { it -> fromAnimeBase(it) }

    fun getAnimeEpisode(id: Int, page: Int = 1): Mono<AnimeEpisodes> {
        return jikan.query().anime().episodes(id, page).execute()
    }

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
            .map { anime -> fromAnimeEpisode(malId, anime) }
    }

}