package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import net.sandrohc.jikan.model.anime.Anime


fun Anime.toAnimeModel() = fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Anime(
    malId.toLong(),
    url,
    images.preferredImageUrl,
    title,
    type,
    episodes,
    status,
    score,
    season,
    year,
    broadcast.string,
    rank,
)

fun Anime.toAnimeDTO() = AnimeDTO(
    malId.toLong(),
    url,
    images.preferredImageUrl,
    title,
    type,
    episodes,
    status,
    score,
    season,
    year,
    broadcast.string,
    rank
)