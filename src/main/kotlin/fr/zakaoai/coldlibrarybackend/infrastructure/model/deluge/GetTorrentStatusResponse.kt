package fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge



data class GetTorrentStatusResponse(val result: GetTorrentStatusResult, val error: DelugeError?, val id: Int)
