package fr.zakaoai.coldlibrarybackend.model.dto.response

import java.time.LocalDate


data class AnimeEpisodeTorrentDTO(
    var malId: Int,
    var episodeNumber: Int,
    var title: String,
    var date: LocalDate,
    var torrentLink: String,
    var torrentId: Int,
    var torrentSize: String,
    var seeders: Int,
    var leechers: Int,
    var completed: Int,
)