package fr.zakaoai.coldlibrarybackend.infrastructure.db.services

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Log
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface LogRepository : ReactiveCrudRepository<Log, Long>