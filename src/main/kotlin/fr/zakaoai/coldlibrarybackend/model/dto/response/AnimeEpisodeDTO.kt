package fr.zakaoai.coldlibrarybackend.model.dto.response


import java.time.LocalDateTime

data class AnimeEpisodeDTO(
    val id: Long,
    val malId: Long,
    val episodeNumber: Int,
    val title: String,
    val url: String?,
    val date: LocalDateTime?,
)

