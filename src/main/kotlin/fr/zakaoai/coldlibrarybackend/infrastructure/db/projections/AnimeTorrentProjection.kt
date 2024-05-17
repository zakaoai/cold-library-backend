package fr.zakaoai.coldlibrarybackend.infrastructure.db.projections

import java.time.DayOfWeek

data class AnimeTorrentProjection(
    val malId: Long,
    val lastEpisodeOnServer: Int,
    val searchWords: String,
    val dayOfRelease: DayOfWeek,
    val deltaEpisode: Int,
    val torrentPath: String,
    val title: String,
    val episodes: Int?,
)