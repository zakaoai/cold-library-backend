package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import fr.zakaoai.coldlibrarybackend.enums.StorageState
import net.sandrohc.jikan.model.anime.AnimeType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("animes")
data class Anime(
    @Id
    val id: Long? = null,
    var malId: Int,
    var title: String,
    var url: String,
    var imageUrl: String = "",
    var type: AnimeType = AnimeType.MUSIC,
    var nbEpisodes: Int = 0,
    var storageState: StorageState = StorageState.FLUX_FROID,
    var isComplete: Boolean,
    var lastAvaibleEpisode: Int? = null,
) {
    fun toAnimeDTO(): fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO {
        return fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO(
            malId,
            title,
            url,
            imageUrl,
            type,
            nbEpisodes,
            storageState,
            isComplete,
            lastAvaibleEpisode
        )
    }
}





