package fr.zakaoai.coldlibrarybackend.model.dto.response

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.TrackedAnimeTorrent
import java.time.DayOfWeek

class TrackedAnimeTorrentDTO(
    var malId: Int,
    var lastEpisodeOnServer: Int,
    var searchWords: String,
    var dayOfRelease: DayOfWeek,
) {
    fun toModel(withId: Long? = null) =
        TrackedAnimeTorrent(withId, malId, lastEpisodeOnServer, searchWords, dayOfRelease)
}