package fr.zakaoai.coldlibrarybackend.infrastructure

import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO
import net.sandrohc.jikan.model.anime.AnimeEpisode
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface JikanAPIService {

    fun searchAnime(search: String): Flux<AnimeDTO>

    fun getAnimeById(id: Int): Mono<AnimeDTO>

    fun getAnimeEpisodesPage(id: Int): Flux<AnimeEpisode>

    fun getAnimeEpisodesByAnimeIdAndEpisodeNumber(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeDTO>

}