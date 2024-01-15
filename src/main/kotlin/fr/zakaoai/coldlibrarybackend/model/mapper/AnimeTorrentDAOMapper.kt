package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeTorrent
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeTorrentDTO

fun AnimeTorrent.toAnimeTorrentDTO() = AnimeTorrentDTO(malId, lastEpisodeOnServer, searchWords, dayOfRelease, deltaEpisode, torrentPath)