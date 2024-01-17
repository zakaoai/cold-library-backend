package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("\"AnimeEpisodeTorrent\"")
class AnimeEpisodeTorrent(
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
        @Column("id_AnimeEpisode")
        val idAnimeEpisode: Long,
        val malId: Long
)
