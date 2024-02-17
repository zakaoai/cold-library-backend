package fr.zakaoai.coldlibrarybackend.model.dto.response


import fr.zakaoai.coldlibrarybackend.enums.RequestStatus
import fr.zakaoai.coldlibrarybackend.enums.RequestType
import java.time.LocalDateTime

data class RequestDTO(
    val id: Long,
    val malId: Long,
    val animeTitle: String,
    val type: RequestType,
    val state: RequestStatus,
    val date: LocalDateTime,
    val creator: String,
    val assignedUser: String? = null
)
