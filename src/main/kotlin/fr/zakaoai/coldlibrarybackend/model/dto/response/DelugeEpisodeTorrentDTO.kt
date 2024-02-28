package fr.zakaoai.coldlibrarybackend.model.dto.response

data class DelugeEpisodeTorrentDTO(
    val torrentHash: String,
    val progress: Float,
    val torrentId: Int
)
