package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.projections.AnimeTorrentProjection
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeTorrentDTO

fun AnimeTorrentProjection.toAnimeTorrentDTO() =
    AnimeTorrentDTO(malId, lastEpisodeOnServer, searchWords, dayOfRelease, deltaEpisode, torrentPath, title, (episodes  == lastEpisodeOnServer))