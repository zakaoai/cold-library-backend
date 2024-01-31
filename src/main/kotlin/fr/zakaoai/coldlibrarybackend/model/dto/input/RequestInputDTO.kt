package fr.zakaoai.coldlibrarybackend.model.dto.input

import fr.zakaoai.coldlibrarybackend.enums.RequestStatus
import fr.zakaoai.coldlibrarybackend.enums.RequestType
import java.time.LocalDateTime

data class RequestInputDTO(
    val id: Long? = null,
    val malId: Long,
    val type: RequestType,
    val state: RequestStatus,
    val date: LocalDateTime = LocalDateTime.now(),
    val userId: String?,
    val assignedUserId: String? = null
)
