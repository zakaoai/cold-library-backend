package fr.zakaoai.coldlibrarybackend.model.mapper

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.User
import fr.zakaoai.coldlibrarybackend.model.dto.response.UserDTO

fun User.toUserDTO() = UserDTO(userId, name, email, malUsername)