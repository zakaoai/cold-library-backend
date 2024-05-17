package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.projections.AnimeTorrentProjection
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface AnimeTorrentRepository : ReactiveCrudRepository<AnimeTorrent, Long> {

    @Query("SELECT at.*, a.title, a.episodes FROM cold_library.\"AnimeTorrent\" at NATURAL JOIN cold_library.\"AnimeInServer\" ais NATURAL JOIN cold_library.\"Anime\" a  WHERE ais.is_downloading IS TRUE")
    fun getAllDownloadingAnime(): Flux<AnimeTorrentProjection>
}