package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import net.sandrohc.jikan.model.anime.AnimeStatus
import net.sandrohc.jikan.model.anime.AnimeType
import net.sandrohc.jikan.model.season.Season
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table


@Table(value = "\"Anime\"", schema = "cold_library")
data class Anime(
    @Id
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
    @kotlin.jvm.Transient @Transient
    @Value("null")
    val isNew: Boolean? = null
) : Persistable<Long> {


    override fun getId(): Long = this.malId

    override fun isNew(): Boolean = this.isNew ?: false
}