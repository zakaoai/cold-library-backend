package fr.zakaoai.coldlibrarybackend.anime.DTO


import fr.zakaoai.coldlibrarybackend.anime.enums.StorageState
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import net.sandrohc.jikan.model.anime.AnimeType

data class AnimeDTO(
    val malId: Int,
    var title: String,
    var url: String,
    var imageUrl: String?,
    var type: AnimeType?,
    var nbEpisodes: Int?,
    var storageState: StorageState? = null,
    var isComplete: Boolean? = null,
    var lastAvaibleEpisode: Int? = null,
) {
    companion object {
        fun fromAnimeBase(anime: net.sandrohc.jikan.model.anime.Anime): AnimeDTO = AnimeDTO(
            anime.malId,
            anime.title,
            anime.url,
            anime.images.preferredImageUrl,
            anime.type,
            anime.episodes,
            null,
            null,
            anime.episodes
        )

    }

    fun toModel(withId: Long? = null) =
        Anime(
            withId,
            malId,
            title,
            url,
            imageUrl ?: "",
            type ?: AnimeType.MUSIC,
            nbEpisodes ?: 0,
            storageState ?: StorageState.FLUX_FROID,
            isComplete ?: (nbEpisodes != 0),
            lastAvaibleEpisode ?: 0
        )
}


data class ErrorMessage(val message: String)