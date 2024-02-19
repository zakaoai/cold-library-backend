package fr.zakaoai.coldlibrarybackend.model.mapper

import net.sandrohc.jikan.model.anime.AnimeEpisode

fun AnimeEpisode.toAnimeEpisode(malId: Long) = fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisode(
    null,
    malId,
    this.malId,
    if (title.isEmpty()) "Episode ${this.malId}" else title,
    url,
    aired?.toLocalDateTime()
)