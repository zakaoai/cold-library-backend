package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeTorrent
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AnimeTorrentRepository : ReactiveCrudRepository<AnimeTorrent, Long>{

    @Query("SELECT at.* FROM cold_library.\"AnimeTorrent\" at NATURAL JOIN cold_library.\"AnimeInServer\" ais  WHERE ais.is_downloading IS TRUE")
   fun getAllDownloadingAnime(): Flux<AnimeTorrent>
}