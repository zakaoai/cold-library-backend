package fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge

data class AddTorrentResponse(val result: String?, val error: DelugeError?, val id: Int)
