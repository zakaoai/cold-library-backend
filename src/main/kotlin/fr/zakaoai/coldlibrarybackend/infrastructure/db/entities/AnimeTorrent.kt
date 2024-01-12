package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.DayOfWeek

@Table("\"AnimeTorrent\"")
data class AnimeTorrent(
    @Id
    val malId: Long,
    val lastEpisodeOnServer: Int,
    val searchWords: String,
    val dayOfRelease: DayOfWeek,
    val delta: Int,
    val torrentPath: String,
)