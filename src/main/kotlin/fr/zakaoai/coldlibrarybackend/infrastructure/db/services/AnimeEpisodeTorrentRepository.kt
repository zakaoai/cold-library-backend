package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeEpisodeTorrentRepository : ReactiveCrudRepository<AnimeEpisodeTorrent, Long> {

    @Query("SELECT aet.*, ae.episode_number, d.progress " +
            "FROM cold_library.\"AnimeEpisodeTorrent\" aet " +
            "INNER JOIN cold_library.\"AnimeEpisode\" ae ON aet.id_anime_episode=ae.id " +
            "LEFT JOIN cold_library.\"DelugeEpisodeTorrent\" d ON aet.torrent_id = d.torrent_id " +
            "WHERE ae.\"mal_id\" = :malId")
    fun findByMalIdWithEpisodeNumber(@Param("malId") malId: Long): Flux<AnimeEpisodeTorrentDTO>

    fun findByMalId(malId: Long): Flux<AnimeEpisodeTorrent>

    @Query("SELECT aet.* " +
            "FROM cold_library.\"AnimeEpisodeTorrent\" aet " +
            "INNER JOIN cold_library.\"AnimeEpisode\" ae ON aet.id_anime_episode=ae.id " +
            "WHERE ae.\"mal_id\" = :malId AND ae.\"episode_number\" = :episodeNumber")
    fun findByMalIdAndEpisodeNumber(
        @Param("malId") malId: Long,
        @Param("episodeNumber") episodeNumber: Int
    ): Mono<AnimeEpisodeTorrent>

    fun deleteByMalId(malId: Long): Mono<Void>

    fun deleteByMalIdAndIdAnimeEpisode(malId: Long, idAnimeEpisode: Long): Mono<Void>
}