package fr.zakaoai.coldlibrarybackend.model.dto.response

data class UserDTO(    val id: String,
                       val name: String,
                       val email: String,
                       val malUsername: String? = null)
