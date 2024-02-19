package fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge

data class AuthLoginResponse(val result: Boolean, val error: DelugeError?, val id: Int)
