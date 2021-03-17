package fr.zakaoai.coldlibrarybackend.anime.DTO


import fr.zakaoai.coldlibrarybackend.anime.enums.StorageState
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import net.sandrohc.jikan.model.anime.AnimeBase
import net.sandrohc.jikan.model.enums.AnimeType

data class AnimeDTO(
    val malId: Int? = null,
    var title: String? = null,
    var url: String? = null,
    var imageUrl: String? = null,
    var type: AnimeType? = null,
    var nbEpisodes: Int? = null,
    var storageState: StorageState? = StorageState.FLUX_FROID,
    var isComplete: Boolean? = null,
    var lastAvaibleEpisode: Int? = null,
)

fun fromAnimeBase(anime: AnimeBase): AnimeDTO {
    return AnimeDTO(anime.malId,anime.title, anime.url, anime.imageUrl, anime.type, anime.episodes,StorageState.FLUX_FROID,null,anime.episodes)
}

fun AnimeDTO.toModel(withId: Long? = null) =
    Anime(withId,this.malId, this.title, this.url, this.imageUrl, this.type, this.nbEpisodes,this.storageState,this.isComplete,this.lastAvaibleEpisode)

data class ErrorMessage(val message: String)