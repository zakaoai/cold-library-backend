package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Log
import fr.zakaoai.coldlibrarybackend.model.dto.response.LogDTO
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface LogRepository : ReactiveCrudRepository<Log, Long>{

    @Query("SELECT * FROM cold_library.\"Log\" NATURAL JOIN cold_library.\"User\"")
    fun findAllWithUserInformation() : Flux<LogDTO>

    @Query("SELECT * FROM cold_library.\"Log\" l NATURAL JOIN cold_library.\"User\" u  where l.user_id = :userId")
    fun findByUserId(@Param("userId") userId: String): Flux<LogDTO>
}