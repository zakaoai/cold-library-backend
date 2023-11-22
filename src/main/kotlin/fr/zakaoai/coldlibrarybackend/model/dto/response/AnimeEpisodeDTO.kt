package fr.zakaoai.coldlibrarybackend.model.dto.response

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

