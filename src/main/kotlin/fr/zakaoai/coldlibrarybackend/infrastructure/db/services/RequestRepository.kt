package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Request
import fr.zakaoai.coldlibrarybackend.model.dto.response.RequestDTO
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RequestRepository : ReactiveCrudRepository<Request, Long> {

    companion object {
        const val getWithInformation: String =
            "SELECT r.*, creator.name as \"creator\", assignedUser.name as \"assigned_user\", anime.title as \"anime_title\"  FROM cold_library.\"Request\" r INNER JOIN cold_library.\"User\" creator ON r.user_id = creator.user_id LEFT JOIN cold_library.\"User\" assignedUser ON r.assigned_user_id = assignedUser.user_id INNER JOIN cold_library.\"Anime\" anime on r.mal_id = anime.mal_id"
    }

    @Query("$getWithInformation WHERE r.user_id = :creatorId")
    fun findByCreatorId(@Param("creatorId") creatorId: String): Flux<RequestDTO>

    @Query("$getWithInformation WHERE r.assigned_user_id = :assignedUserId")
    fun findByAssignedUserId(@Param("assignedUserId") assignedUserId: String): Flux<RequestDTO>

    @Query("$getWithInformation  WHERE r.id = :id")
    fun findByIdWithInformation(@Param("id") id: Long): Mono<RequestDTO>

    @Query(getWithInformation)
    fun findAllWithInformation(): Flux<RequestDTO>


}