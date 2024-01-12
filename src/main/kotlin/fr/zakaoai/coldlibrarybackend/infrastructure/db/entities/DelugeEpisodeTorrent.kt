package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("\"DelugeEpisodeTorrent\"")
data class DelugeEpisodeTorrent(
    @Id
    var id: Long? = null,
    val torrent_hash: String,
    val progress: Int,
    val id_AnimeEpisodeTorrent: Long
)
