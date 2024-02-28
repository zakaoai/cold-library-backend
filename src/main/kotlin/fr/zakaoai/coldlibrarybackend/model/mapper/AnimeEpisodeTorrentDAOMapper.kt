package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO

fun AnimeEpisodeTorrent.toAnimeEpisodeTorrentDTO(episodeNumber: Int, progress: Float? = null) = AnimeEpisodeTorrentDTO(
    id,
    title,
    date,
    torrentLink,
    torrentId,
    torrentSize,
    seeders,
    leechers,
    completed,
    episodeNumber,
    malId,
    progress
)