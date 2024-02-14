package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeInServer
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeWithServerInformationDTO
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeInServerRepository : ReactiveCrudRepository<AnimeInServer, Long> {

    @Query("SELECT * FROM cold_library.\"AnimeInServer\" NATURAL JOIN cold_library.\"Anime\"")
    fun findAllWithAnimeInformation(): Flux<AnimeWithServerInformationDTO>

    @Query("SELECT * FROM cold_library.\"AnimeInServer\" ais NATURAL JOIN cold_library.\"Anime\" a WHERE a.\"mal_id\" = :malId")
    fun findWithAnimeInformation(@Param("malId") malId: Long): Mono<AnimeWithServerInformationDTO>
}