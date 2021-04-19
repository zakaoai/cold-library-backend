package fr.zakaoai.coldlibrarybackend.torrent.repository.entity

import fr.zakaoai.coldlibrarybackend.torrent.DTO.AnimeEpisodeTorrentDTO
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
        this.malId,
        this.episodeNumber,
        this.title,
        this.dateSortie,
        this.torrentLink,
        this.torrentId,
        this.torrentSize,
        this.seeders,
        this.leechers,
        this.completed
    )
}
