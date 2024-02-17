package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO

fun AnimeEpisodeTorrentDTO.toModel(idAnimeEpisode: Long) = AnimeEpisodeTorrent(
    null,
    title,
    date,
    torrentLink,
    torrentId,
    torrentSize,
    seeders,
    leechers,
    completed,
    idAnimeEpisode,
    malId
)