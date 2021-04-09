package fr.zakaoai.coldlibrarybackend.torrent.repository.entity

import fr.zakaoai.coldlibrarybackend.torrent.DTO.AnimeEpisodeTorrentDTO
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("animeepisodetorrent")
class AnimeEpisodeTorrent(
    @Id
    val id: Long? = null,
    var malId: Int ,
    var episodeNumber: Int,
    var title: String,
    var date: Date,
    var torrentLink: String,
    var torrentId: Int,
) {
    fun toAnimeEpisodeTorrentDTO() = AnimeEpisodeTorrentDTO(this.malId,this.episodeNumber,this.title,this.date,this.torrentLink,this.torrentId)
}
