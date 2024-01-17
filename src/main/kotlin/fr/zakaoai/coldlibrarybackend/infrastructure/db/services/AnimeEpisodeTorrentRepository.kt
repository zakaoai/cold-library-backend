package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeEpisodeTorrentRepository : ReactiveCrudRepository<AnimeEpisodeTorrent, Long> {

    fun findByMalId(malId: Long): Flux<AnimeEpisodeTorrent>

    @Query("SELECT aet.* FROM cold_library.\"AnimeEpisodeTorrent\" aet NATURAL JOIN cold_library.\"AnimeEpisode\" ae WHERE ae.\"mal_id\" = :malId AND ae.\"episode_number\" = :episodeNumber")
    fun findByMalIdAndEpisodeNumber(
        @Param("malId") malId: Long,
        @Param("episodeNumber") episodeNumber: Int
    ): Mono<AnimeEpisodeTorrent>

    fun deleteByMalId(malId: Long): Mono<Void>

    fun deleteByMalIdAndIdAnimeEpisode(malId: Long, idAnimeEpisode: Long): Mono<Void>
}