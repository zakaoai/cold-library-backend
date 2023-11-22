package fr.zakaoai.coldlibrarybackend.model.mapper

import de.kaysubs.tracker.nyaasi.model.TorrentPreview
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
import java.time.LocalDate
import java.time.ZoneId

fun TorrentPreview.toAnimeEpisodeTorrentDTO(malId: Int, episodeNumber: Int) = AnimeEpisodeTorrentDTO(
    malId,
    episodeNumber,
    title,
    LocalDate.ofInstant(
        date.toInstant(), ZoneId.systemDefault()
    ),
    downloadLink.toString(),
    id,
    size.toString(),
    seeders,
    leechers,
    completed
)