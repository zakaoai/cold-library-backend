package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import net.sandrohc.jikan.model.anime.Anime


fun Anime.toAnimeDTO() = AnimeDTO(
    malId,
    title,
    url,
    images.preferredImageUrl,
    type,
    episodes,
    null,
    null,
    episodes
)