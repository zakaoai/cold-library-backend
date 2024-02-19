package fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge

data class GetTorrentStatusResult(
    val download_location: String?,
    val hash: String?,
    val progress: Float?
)
