package fr.zakaoai.coldlibrarybackend.anime.DTO

import fr.zakaoai.coldlibrarybackend.anime.repository.entity.AnimeEpisode
import java.time.OffsetDateTime
import net.sandrohc.jikan.model.anime.AnimeEpisode as JikanAnimeEpisode

data class AnimeEpisodeDTO(
        var malId: Int,
        var title: String? = null,
        var episodeNumber: Int,
        var dateSortie: OffsetDateTime? = null,
        var urlTorrent: String? = null,
) {
    fun toModel(withId: Long? = null) =
            AnimeEpisode(withId, malId, title, episodeNumber, dateSortie, urlTorrent)

}

fun fromAnimeEpisode(malId: Int, anime: JikanAnimeEpisode): AnimeEpisodeDTO {
    return AnimeEpisodeDTO(malId, anime.title, anime.malId, anime.aired)
}

