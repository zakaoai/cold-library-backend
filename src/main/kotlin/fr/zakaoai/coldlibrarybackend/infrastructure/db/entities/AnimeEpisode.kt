package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

@Table("animeEpisodes")
data class AnimeEpisode(
    @Id
    val id: Long?,
    val malId: Int,
    val title: String? = null,
    val episodeNumber: Int,
    val dateSortie: OffsetDateTime? = null,
    val urlTorrent: String? = null,
) {
    fun toAnimeEpisodeDTO(): fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO {
        return fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO(
            malId,
            title,
            episodeNumber,
            dateSortie,
            urlTorrent
        )
    }

}


