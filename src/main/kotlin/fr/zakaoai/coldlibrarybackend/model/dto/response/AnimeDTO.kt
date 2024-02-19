package fr.zakaoai.coldlibrarybackend.model.dto.response

import net.sandrohc.jikan.model.anime.AnimeStatus
import net.sandrohc.jikan.model.anime.AnimeType
import net.sandrohc.jikan.model.season.Season

data class AnimeDTO(
    var malId: Long,
    var malUrl: String,
    var malImg: String?,
    var title: String,
    var type: AnimeType?,
    val episodes: Int?,
    val status: AnimeStatus?,
    val score: Double?,
    val season: Season?,
    val year: Int?,
    val broadcast: String?,
    val rank: Int?,
)
