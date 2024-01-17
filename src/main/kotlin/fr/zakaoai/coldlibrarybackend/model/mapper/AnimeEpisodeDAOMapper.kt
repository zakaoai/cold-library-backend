package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisode
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO

fun AnimeEpisode.toAnimeEpisodeDTO() = AnimeEpisodeDTO(id!!, malId, episodeNumber, title, url, date)