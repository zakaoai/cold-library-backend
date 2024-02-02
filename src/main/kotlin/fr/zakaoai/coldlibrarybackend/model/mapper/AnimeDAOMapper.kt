package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.enums.StorageState
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Anime
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeInServer
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO

fun Anime.toAnimeInServer() = AnimeInServer( malId, StorageState.FLUX_FROID, false, episodes != 0, episodes?.or(0) ?: 0)

fun Anime.toAnimeDTO() = AnimeDTO(
    malId,
    malUrl,
    malImg,
    title,
    type,
    episodes,
    status,
    score,
    season,
    year,
    broadcast,
    rank)

fun Anime.toAnimeDTO(animeInServer: AnimeInServer) = AnimeDTO(
    malId,
    malUrl,
    malImg,
    title,
    type,
    episodes,
    status,
    score,
    season,
    year,
    broadcast,
    rank,
    animeInServer.storageState,
    animeInServer.isDownloading,
    animeInServer.isComplete,
    animeInServer.lastAvaibleEpisode,
    animeInServer.addedOnServer!!,
)