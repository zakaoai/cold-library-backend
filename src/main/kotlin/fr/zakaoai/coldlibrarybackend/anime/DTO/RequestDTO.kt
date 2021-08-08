package fr.zakaoai.coldlibrarybackend.anime.DTO

import fr.zakaoai.coldlibrarybackend.anime.enums.RequestStatus
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Request

data class RequestDTO(
        val animeId: Int? = null,
        val userId: Long? = null,
        val requestStatus: RequestStatus? = null
)

fun RequestDTO.toModel(withId: Long? = null) =
        Request(withId, animeId, userId, requestStatus)