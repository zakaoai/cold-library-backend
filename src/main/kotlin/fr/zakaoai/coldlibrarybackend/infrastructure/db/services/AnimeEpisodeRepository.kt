package fr.zakaoai.coldlibrarybackend.infrastructure.db.services


import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisode
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeEpisodeRepository : ReactiveCrudRepository<AnimeEpisode, Long> {

    fun findByMalIdAndEpisodeNumber(malId: Int, EpisodeNumber: Int): Mono<AnimeEpisode>

    fun deleteByMalId(malId: Int): Mono<Void>

    @Query("SELECT *  FROM animeEpisodes WHERE animeEpisodes.MAL_ID = :malId ")
    fun getWithMalId( @Param("malId") malId : String): Flux<AnimeEpisode>
}