package fr.zakaoai.coldlibrarybackend.infrastructure

import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO
import net.sandrohc.jikan.model.anime.Anime
import net.sandrohc.jikan.model.anime.AnimeEpisode
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface JikanAPIService {

    fun searchAnime(search: String): Flux<Anime>

    fun getAnimeById(id: Long): Mono<Anime>

    fun getAnimeEpisodesPage(id: Long): Flux<AnimeEpisode>

    fun getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId: Long, episodeNumber: Int): Flux<AnimeEpisode>

}