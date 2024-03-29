package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("\"Log\"")
data class Log(
    @Id
    val id: Long? = null,
    val action: String,
    val date: LocalDateTime? = LocalDateTime.now(),
    val userId: String
)
