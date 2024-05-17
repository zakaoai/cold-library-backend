package fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge

data class ConnectedResponse(
    val result: Boolean?,
    val error: DelugeError?,
    val id: Int
)
