package fr.zakaoai.coldlibrarybackend.anime.repository.entity

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
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
    fun toAnimeEpisodeDTO(): AnimeEpisodeDTO {
        return AnimeEpisodeDTO(this.malId, this.title, this.episodeNumber, this.dateSortie, this.urlTorrent)
    }

}


