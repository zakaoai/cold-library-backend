package fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge

data class WebConnectResponse(
    val result: List<String>?,
    val error: DelugeError?,
    val id: Int
)
