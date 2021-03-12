package fr.zakaoai.coldlibrarybackend.anime.DTO


import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import net.sandrohc.jikan.model.anime.AnimeBase
import net.sandrohc.jikan.model.enums.AnimeType

data class AnimeDTO(
    val malId: Int? = null,
    val title: String? = null,
    val url: String? = null,
    val imageUrl: String? = null,
    val type: AnimeType? = null,
    val episodes: Int? = 0,
    val state: State? = null
)

fun fromAnimeBase(anime: AnimeBase): AnimeDTO {
    return AnimeDTO(anime.malId,anime.title, anime.url, anime.imageUrl, anime.type, anime.episodes)
}

fun AnimeDTO.toModel(withId: Long? = null) =
    Anime(withId,this.malId, this.title, this.url, this.imageUrl, this.type, this.episodes)

data class ErrorMessage(val message: String)