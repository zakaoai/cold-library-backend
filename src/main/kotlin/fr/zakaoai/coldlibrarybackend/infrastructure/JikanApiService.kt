package fr.zakaoai.coldlibrarybackend.infrastructure

import net.sandrohc.jikan.model.anime.Anime
import net.sandrohc.jikan.model.anime.AnimeEpisode
import net.sandrohc.jikan.model.season.Season
import net.sandrohc.jikan.model.season.SeasonEntry
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface JikanApiService {
    fun searchAnime(search: String): Flux<Anime>

    fun getAnimeById(id: Long): Mono<Anime>

    fun getAnimeEpisodesPage(id: Long): Flux<AnimeEpisode>

    fun getAnimeEpisodesFromEpisodeByAnimeIdAndEpisodeNumber(malId: Long, episodeNumber: Int): Flux<AnimeEpisode>

    fun getAnimeEpisodeByAnimeIdAndEpisodeNumber(malId: Long, episodeNumber: Int): Mono<AnimeEpisode>

    fun getAnimeBySeason(year: Int, season: Season, page: Int?): Flux<Anime>

    fun getSeason(): Flux<SeasonEntry>

}