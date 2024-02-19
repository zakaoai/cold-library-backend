package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeInServer
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeInServerDTO

fun AnimeInServer.toAnimeInServerDTO() =
    AnimeInServerDTO(malId, storageState, isDownloading, isComplete, lastAvaibleEpisode, addedOnServer!!)