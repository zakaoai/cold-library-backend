package fr.zakaoai.coldlibrarybackend.model.mapper

import de.kaysubs.tracker.nyaasi.model.TorrentInfo
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO

fun TorrentInfo.toAnimeEpisodeTorrentDTO(torrentId: Int, malId: Int, episodeNumber: Int) = AnimeEpisodeTorrentDTO(
    malId,
    episodeNumber,
    title,
    java.time.LocalDate.ofInstant(
        date.toInstant(), java.time.ZoneId.systemDefault()
    ),
    downloadLink.toString(),
    torrentId,
    size.toString(),
    seeders,
    leechers,
    completed
)