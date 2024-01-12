package fr.zakaoai.coldlibrarybackend.infrastructure.db.services


import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Anime
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono


interface AnimeRepository : ReactiveCrudRepository<Anime, Long>