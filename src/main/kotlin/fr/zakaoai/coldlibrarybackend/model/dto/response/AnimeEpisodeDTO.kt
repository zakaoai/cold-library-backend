package fr.zakaoai.coldlibrarybackend.model.dto.response


import java.time.OffsetDateTime

data class AnimeEpisodeDTO(
    var malId: Int,
    var title: String? = null,
    var episodeNumber: Int,
    var dateSortie: OffsetDateTime? = null,
    var urlTorrent: String? = null,
)

