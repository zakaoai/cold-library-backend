package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Request
import fr.zakaoai.coldlibrarybackend.model.dto.input.RequestInputDTO

fun RequestInputDTO.toRequest(userId: String) = Request(id, malId, type, state, date, userId, assignedUserId)