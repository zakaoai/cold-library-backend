package fr.zakaoai.coldlibrarybackend.torrent.DTO

import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.TrackedAnimeTorrent
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