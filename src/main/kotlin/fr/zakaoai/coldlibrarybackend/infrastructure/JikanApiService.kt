package fr.zakaoai.coldlibrarybackend.infrastructure

import net.sandrohc.jikan.model.anime.Anime
import net.sandrohc.jikan.model.anime.AnimeEpisode
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface JikanAPIService {

    fun searchAnime(search: String): Flux<Anime>

    fun getAnimeById(id: Long): Mono<Anime>

    fun getAnimeEpisodesPage(id: Long): Flux<AnimeEpisode>

    fun getAnimeEpisodesFromEpisodeByAnimeIdAndEpisodeNumber(malId: Long, episodeNumber: Int): Flux<AnimeEpisode>

    fun getAnimeEpisodeByAnimeIdAndEpisodeNumber(malId: Long, episodeNumber: Int): Mono<AnimeEpisode>

}