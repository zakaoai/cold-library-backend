package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("\"Request\"")
data class Request(
    @Id
    val id: Long? = null,
    val malId: Long,
    val state: String,
    val date: LocalDateTime? = LocalDateTime.now(),
    val id_User: Long,
)

