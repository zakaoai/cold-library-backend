package fr.zakaoai.coldlibrarybackend.anime.repository.entity

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.enums.StorageState
import net.sandrohc.jikan.model.enums.AnimeType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("animes")
data class Anime(
    @Id
    val id: Long? = null,
    var malId: Int? = null,
    var title: String? = null,
    var url: String? = null,
    var imageUrl: String? = null,
    var type: AnimeType? = null,
    var nbEpisodes: Int? = null,
    var storageState: StorageState? = StorageState.FLUX_FROID,
    var isComplete: Boolean? = null,
    var lastAvaibleEpisode: Int? = null,
) {
    fun toAnimeDTO(): AnimeDTO {
        return AnimeDTO(
            this.malId,
            this.title,
            this.url,
            this.imageUrl,
            this.type,
            this.nbEpisodes,
            this.storageState,
            this.isComplete,
            this.lastAvaibleEpisode
        )
    }
}





