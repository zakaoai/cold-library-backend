package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import fr.zakaoai.coldlibrarybackend.enums.RequestStatus
import fr.zakaoai.coldlibrarybackend.enums.RequestType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("\"Request\"")
data class Request(
    @Id
    val id: Long? = null,
    val malId: Long,
    val type: RequestType,
    val state: RequestStatus,
    val date: LocalDateTime = LocalDateTime.now(),
    val userId: String,
    val assignedUserId: String? = null
)

