package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import fr.zakaoai.coldlibrarybackend.model.dto.response.TrackedAnimeTorrentDTO
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.DayOfWeek

@Table("trackedanimetorrent")
class TrackedAnimeTorrent(
        @Id
        var id: Long? = null,
        var malId: Int,
        var lastEpisodeOnServer: Int,
        var searchWords: String,
        var dayOfRelease: DayOfWeek,
) {
    fun toTrackedAnimeTorrentDTO() = TrackedAnimeTorrentDTO(malId, lastEpisodeOnServer, searchWords, dayOfRelease)
}