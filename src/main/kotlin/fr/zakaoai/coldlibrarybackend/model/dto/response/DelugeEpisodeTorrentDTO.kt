package fr.zakaoai.coldlibrarybackend.model.dto.response

data class DelugeEpisodeTorrentDTO(
    val torrentHash: String,
    val progress: Float,
    val idAnimeEpisodeTorrent: Long,
    val torrentId: Int
)
