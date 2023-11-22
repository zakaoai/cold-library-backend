package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO
import net.sandrohc.jikan.model.anime.AnimeEpisode

fun AnimeEpisode.toAnimeEpisode(malId: Int) = AnimeEpisodeDTO(malId, title, malId, aired)