package fr.zakaoai.coldlibrarybackend.anime.repository

import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono


interface AnimeRepository : ReactiveCrudRepository<Anime, Long> {

    fun findByMalId(malId : Int?): Mono<Anime>
}