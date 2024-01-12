package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeTorrent
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface AnimeTorrentRepository : ReactiveCrudRepository<AnimeTorrent, Long>