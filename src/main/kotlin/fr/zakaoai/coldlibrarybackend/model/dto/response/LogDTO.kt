package fr.zakaoai.coldlibrarybackend.model.dto.response

import java.time.LocalDateTime

data class LogDTO(
    val id: Long,
    val action: String,
    val date: LocalDateTime,
    val userId: String,
    val name: String,
    val email: String,
)