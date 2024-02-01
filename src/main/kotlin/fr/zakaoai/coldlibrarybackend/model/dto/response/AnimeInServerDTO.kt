package fr.zakaoai.coldlibrarybackend.model.dto.response

import fr.zakaoai.coldlibrarybackend.enums.StorageState
import java.time.LocalDateTime

data class AnimeInServerDTO(
    val malId: Long,
    val storageState: StorageState,
    val isDownloading: Boolean,
    val isComplete: Boolean,
    val lastAvaibleEpisode: Int,
    val addedOnServer: LocalDateTime
)
