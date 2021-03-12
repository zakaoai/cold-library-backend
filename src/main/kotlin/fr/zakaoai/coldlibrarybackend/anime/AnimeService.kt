package fr.zakaoai.coldlibrarybackend.anime


import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.toModel
import fr.zakaoai.coldlibrarybackend.anime.api.JikanAPIService
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.toAnimeDTO

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*


@Service
class AnimeService(private val repo: AnimeRepository, private val jikanService: JikanAPIService) {

    fun saveAnimeById(id: Int): Mono<AnimeDTO> {

        return repo.findByMalId(id)
            .switchIfEmpty(jikanService.getAnimeById(id).map(AnimeDTO::toModel).flatMap { repo.save(it) }
            ).map(Anime::toAnimeDTO)

    }

    fun deleteById(id: Int): Mono<Void> {
       return repo.findByMalId(id).flatMap(repo::delete)
    }

    fun findByMalId(id: Int): Mono<AnimeDTO> {
        return repo.findByMalId(id).map(Anime::toAnimeDTO)
    }

    fun searchAnime(search: String): Flux<AnimeDTO> {
        return jikanService.searchAnime(search)
            .flatMap { jikanAnime -> repo.findByMalId(jikanAnime.malId).map(Anime::toAnimeDTO).defaultIfEmpty(jikanAnime) }
    }

}