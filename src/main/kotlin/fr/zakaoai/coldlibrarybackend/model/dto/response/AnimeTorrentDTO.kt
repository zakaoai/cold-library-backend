package fr.zakaoai.coldlibrarybackend.model.dto.response

import java.time.DayOfWeek

class AnimeTorrentDTO(
    val malId: Long,
    val lastEpisodeOnServer: Int,
    val searchWords: String,
    val dayOfRelease: DayOfWeek,
    val deltaEpisode: Int,
    val torrentPath: String,
)