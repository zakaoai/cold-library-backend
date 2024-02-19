package fr.zakaoai.coldlibrarybackend.infrastructure.db.services


import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Anime
import org.springframework.data.repository.reactive.ReactiveCrudRepository


interface AnimeRepository : ReactiveCrudRepository<Anime, Long>