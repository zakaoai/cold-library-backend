package fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge

data class GetHostsResponse(
    val result: List<List<Any>>?,
    val error: DelugeError?,
    val id: Int
)
