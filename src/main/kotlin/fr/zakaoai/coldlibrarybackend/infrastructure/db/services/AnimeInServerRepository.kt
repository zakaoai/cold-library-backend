package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeInServer
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeInServerRepository : ReactiveCrudRepository<AnimeInServer, Long> {

    @Query("SELECT * FROM AnimeInServer NATURAL JOIN Anime")
    fun findAllWithAnimeInformation(): Flux<AnimeDTO>

    @Query("SELECT * FROM cold_library.\"AnimeInServer\" ais LEFT JOIN cold_library.\"Anime\" a ON ais.\"malId\"  = a.\"malId\"  WHERE a.\"malId\" = :malId")
    fun findWithAnimeInformation(@Param("malId") malId: Long): Mono<AnimeDTO>
}