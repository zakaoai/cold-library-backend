package fr.zakaoai.coldlibrarybackend.model.dto.response


import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import fr.zakaoai.coldlibrarybackend.enums.StorageState
import net.sandrohc.jikan.model.anime.AnimeStatus
import net.sandrohc.jikan.model.anime.AnimeType
import net.sandrohc.jikan.model.season.Season
import java.time.LocalDateTime

@JsonInclude(Include.NON_NULL)
data class AnimeWithServerInformationDTO(
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
    val storageState: StorageState? = null,
    val isDownloading: Boolean? = null,
    val isComplete: Boolean? = null,
    val lastAvaibleEpisode: Int? = null,
    val addedOnServer: LocalDateTime? = null,
)