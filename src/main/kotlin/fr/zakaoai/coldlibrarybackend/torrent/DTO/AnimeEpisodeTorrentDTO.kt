package fr.zakaoai.coldlibrarybackend.torrent.DTO

import de.kaysubs.tracker.nyaasi.model.TorrentPreview
import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.AnimeEpisodeTorrent
import java.time.LocalDate
import java.time.ZoneId


class AnimeEpisodeTorrentDTO(
    var malId: Int,
    var episodeNumber: Int,
    var title: String,
    var date: LocalDate,
    var torrentLink: String,
    var torrentId: Int,
) {
    constructor(torrent: TorrentPreview, malId: Int, episodeNumber: Int) : this(
        malId, episodeNumber, torrent.title, LocalDate.ofInstant(
            torrent.date.toInstant(), ZoneId.systemDefault()
        ), torrent.downloadLink.toString(), torrent.id
    )

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