package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("animeepisodetorrent")
class AnimeEpisodeTorrent(
        @Id
        var id: Long? = null,
        var malId: Int,
        var episodeNumber: Int,
        var title: String,
        var dateSortie: LocalDate,
        var torrentLink: String,
        var torrentId: Int,
        var torrentSize: String,
        var seeders: Int,
        var leechers: Int,
        var completed: Int,
) {
    fun toAnimeEpisodeTorrentDTO() = AnimeEpisodeTorrentDTO(
            malId,
            episodeNumber,
            title,
            dateSortie,
            torrentLink,
            torrentId,
            torrentSize,
            seeders,
            leechers,
            completed
    )
}
