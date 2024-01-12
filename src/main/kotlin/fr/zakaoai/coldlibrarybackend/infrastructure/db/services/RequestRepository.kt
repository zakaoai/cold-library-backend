package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Request
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface RequestRepository : ReactiveCrudRepository<Request, Long>