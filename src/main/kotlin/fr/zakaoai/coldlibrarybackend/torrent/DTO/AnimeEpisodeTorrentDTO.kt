package fr.zakaoai.coldlibrarybackend.torrent.DTO

import de.kaysubs.tracker.nyaasi.model.TorrentInfo
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
        var torrentSize: String,
        var seeders: Int,
        var leechers: Int,
        var completed: Int,

        ) {
    constructor(torrent: TorrentPreview, malId: Int, episodeNumber: Int) : this(
            malId,
            episodeNumber,
            torrent.title,
            LocalDate.ofInstant(
                    torrent.date.toInstant(), ZoneId.systemDefault()
            ),
            torrent.downloadLink.toString(),
            torrent.id,
            torrent.size.toString(),
            torrent.seeders,
            torrent.leechers,
            torrent.completed
    )
        constructor(torrent: TorrentInfo, torrentId: Int,malId: Int, episodeNumber: Int) : this(
                malId,
                episodeNumber,
                torrent.title,
                LocalDate.ofInstant(
                        torrent.date.toInstant(), ZoneId.systemDefault()
                ),
                torrent.downloadLink.toString(),
                torrentId,
                torrent.size.toString(),
                torrent.seeders,
                torrent.leechers,
                torrent.completed
        )

    fun toModel(withId: Long? = null) = AnimeEpisodeTorrent(
            withId,
            malId,
            episodeNumber,
            title,
            date,
            torrentLink,
            torrentId,
            torrentSize,
            seeders,
            leechers,
            completed
    )
}