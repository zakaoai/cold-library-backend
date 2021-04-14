package fr.zakaoai.coldlibrarybackend.torrent.repository.entity

import fr.zakaoai.coldlibrarybackend.torrent.DTO.AnimeEpisodeTorrentDTO
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.util.*

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
) {
    fun toAnimeEpisodeTorrentDTO() = AnimeEpisodeTorrentDTO(this.malId,this.episodeNumber,this.title,this.dateSortie,this.torrentLink,this.torrentId)
}
