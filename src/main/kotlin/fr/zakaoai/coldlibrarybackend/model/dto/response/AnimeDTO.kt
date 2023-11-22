package fr.zakaoai.coldlibrarybackend.model.dto.response


import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import fr.zakaoai.coldlibrarybackend.enums.StorageState
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