package fr.zakaoai.coldlibrarybackend.anime.DTO

import fr.zakaoai.coldlibrarybackend.anime.repository.entity.AnimeEpisode
import net.sandrohc.jikan.model.anime.AnimeEpisode as JikanAnimeEpisode
import java.time.OffsetDateTime

data class AnimeEpisodeDTO(
    var malId: Int? = null,
    var title: String? = null,
    var episodeNumber: Int? = null,
    var dateSortie: OffsetDateTime? = null,
    var urlTorrent: String? = null,
)

fun fromAnimeEpisode(malId: Int, anime: JikanAnimeEpisode): AnimeEpisodeDTO {
    return AnimeEpisodeDTO(malId, anime.title, anime.episodeId, anime.aired)
}

fun AnimeEpisodeDTO.toModel(withId: Long? = null) =
    AnimeEpisode(withId, this.malId, this.title, this.episodeNumber, this.dateSortie, this.urlTorrent)