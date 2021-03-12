package fr.zakaoai.coldlibrarybackend.anime.api

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.fromAnimeBase
import net.sandrohc.jikan.Jikan
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

}