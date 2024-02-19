package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("\"DelugeEpisodeTorrent\"")
data class DelugeEpisodeTorrent(
    @Id
    var id: Long? = null,
    val torrentHash: String,
    val progress: Float,
    val idAnimeEpisodeTorrent: Long
)
