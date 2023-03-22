package fr.zakaoai.coldlibrarybackend.anime.repository.entity

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.enums.StorageState
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
        var imageUrl: String?,
        var type: AnimeType?,
        var nbEpisodes: Int?,
        var storageState: StorageState = StorageState.FLUX_FROID,
        var isComplete: Boolean,
        var lastAvaibleEpisode: Int? = null,
) {
    fun toAnimeDTO(): AnimeDTO {
        return AnimeDTO(
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





