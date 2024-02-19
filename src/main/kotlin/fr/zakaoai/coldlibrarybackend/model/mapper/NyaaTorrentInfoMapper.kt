package fr.zakaoai.coldlibrarybackend.model.mapper

import de.kaysubs.tracker.nyaasi.model.TorrentInfo
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import java.time.LocalDate
import java.time.ZoneId

fun TorrentInfo.toAnimeEpisodeTorrent(malId: Long, animeEpisodeId: Long, torrentId: Int) = AnimeEpisodeTorrent(
    null,
    title,
    LocalDate.ofInstant(
        date.toInstant(), ZoneId.systemDefault()
    ),
    downloadLink.toString(),
    torrentId,
    size.toString(),
    seeders,
    leechers,
    completed,
    animeEpisodeId,
    malId
)