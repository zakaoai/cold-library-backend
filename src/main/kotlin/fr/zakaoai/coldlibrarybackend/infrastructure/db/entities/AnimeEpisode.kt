package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Table("\"AnimeEpisode\"")
data class AnimeEpisode(
    @Id
    val id: Long? = null,
    val malId: Long,
    val episodeNumber: Int,
    val title: String,
    val url: String? = null,
    val date: LocalDateTime? = null,
)


