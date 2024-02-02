package fr.zakaoai.coldlibrarybackend.model.dto.response

import java.time.LocalDate


data class AnimeEpisodeTorrentDTO(
    var id: Long,
    val title: String,
    val date: LocalDate,
    val torrentLink: String,
    val torrentId: Int,
    val torrentSize: String,
    val seeders: Int,
    val leechers: Int,
    val completed: Int,
    val episodeNumber: Int,
    val malId: Long
)