package fr.zakaoai.coldlibrarybackend.torrent.DTO

import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.AnimeEpisodeTorrent
import java.util.*


class AnimeEpisodeTorrentDTO(
    var malId: Int,
    var episodeNumber: Int,
    var title: String,
    var date: Date,
    var torrentLink: String,
    var torrentId: Int,
) {
    fun toModel(withId: Long? = null) = AnimeEpisodeTorrent(
        withId,
        this.malId,
        this.episodeNumber,
        this.title,
        this.date,
        this.torrentLink,
        this.torrentId
    )
}