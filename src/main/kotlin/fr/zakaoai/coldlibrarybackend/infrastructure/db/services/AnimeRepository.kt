package fr.zakaoai.coldlibrarybackend.infrastructure.db.services


import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Anime
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono


interface AnimeRepository : ReactiveCrudRepository<Anime, Long> {

    fun findByMalId(malId: Int?): Mono<Anime>

    fun deleteByMalId(malId: Int?): Mono<Void>
}