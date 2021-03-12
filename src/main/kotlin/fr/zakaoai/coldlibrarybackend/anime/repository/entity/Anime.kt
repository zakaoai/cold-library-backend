package fr.zakaoai.coldlibrarybackend.anime.repository.entity

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.State
import net.sandrohc.jikan.model.enums.AnimeType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("animes")
data class Anime(
    @Id
    val id: Long? = null,
    val malId: Int? = null,
    val title: String? = null,
    val url: String? = null,
    val imageUrl: String? = null,
    val type: AnimeType? = null,
    val episodes: Int? = 0,
)

fun Anime.toAnimeDTO() : AnimeDTO { return AnimeDTO(this.malId, this.title, this.url, this.imageUrl, this.type, this.episodes,State.FLUX_FROID)
}
