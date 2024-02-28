package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.DelugeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.model.dto.response.DelugeEpisodeTorrentDTO

fun DelugeEpisodeTorrent.toDelugeEpisodeTorrentDTO() = DelugeEpisodeTorrentDTO(torrentHash, progress, idAnimeEpisodeTorrent, torrentId)