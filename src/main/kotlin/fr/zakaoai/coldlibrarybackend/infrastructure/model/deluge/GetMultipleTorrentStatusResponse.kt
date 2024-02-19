package fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge

data class GetMultipleTorrentStatusResponse(
    val result: HashMap<String, GetTorrentStatusResult>?,
    val error: DelugeError?,
    val id: Int
)
