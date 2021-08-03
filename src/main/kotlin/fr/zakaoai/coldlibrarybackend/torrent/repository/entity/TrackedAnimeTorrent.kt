package fr.zakaoai.coldlibrarybackend.torrent.repository.entity

import fr.zakaoai.coldlibrarybackend.torrent.DTO.TrackedAnimeTorrentDTO
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
    fun toTrackedAnimeTorrentDTO() = TrackedAnimeTorrentDTO(this.malId, this.lastEpisodeOnServer, this.searchWords, this.dayOfRelease)
}