package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("\"AnimeEpisodeTorrent\"")
data class AnimeEpisodeTorrent(
    @Id
    var id: Long? = null,
    val title: String,
    val date: LocalDate,
    val torrentLink: String,
    val torrentId: Int,
    val torrentSize: String,
    val seeders: Int,
    val leechers: Int,
    val completed: Int,
    val idAnimeEpisode: Long,
    val malId: Long
)
