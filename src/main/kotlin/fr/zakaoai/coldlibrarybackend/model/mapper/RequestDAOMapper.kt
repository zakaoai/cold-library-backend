package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Anime
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Request
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.User
import fr.zakaoai.coldlibrarybackend.model.dto.response.RequestDTO

fun Request.toRequestDTO(anime: Anime, creator: User, assignedUser: User?) =
    RequestDTO(id!!, malId, anime.title, type, state, date, creator.name, assignedUser?.name)