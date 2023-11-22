package fr.zakaoai.coldlibrarybackend.model.dto.response

import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Request
import fr.zakaoai.coldlibrarybackend.enums.RequestStatus

data class RequestDTO(
    val animeId: Int? = null,
    val userId: Long? = null,
    val requestStatus: RequestStatus? = null
) {
    fun toModel(withId: Long? = null) =
        Request(withId, animeId, userId, requestStatus)
}

