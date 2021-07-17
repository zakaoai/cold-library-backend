package fr.zakaoai.coldlibrarybackend.anime.repository.entity

import fr.zakaoai.coldlibrarybackend.anime.enums.RequestStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("animes")
data class Request(
    @Id
    val id: Long? = null,
    val animeId: Int? = null,
    val userId: Long? = null,
    val requestStatus: RequestStatus? = null
)

