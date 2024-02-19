package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.DelugeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge.AddTorrentResponse

fun AddTorrentResponse.toDelugeEpisodeTorrent(idAnimeEpisodeTorrent: Long) =
    DelugeEpisodeTorrent(null, result!!, 0F, idAnimeEpisodeTorrent)