package fr.zakaoai.coldlibrarybackend.model.dto.response


import fr.zakaoai.coldlibrarybackend.enums.RequestStatus

data class RequestDTO(
    val animeId: Int? = null,
    val userId: Long? = null,
    val requestStatus: RequestStatus? = null
)
