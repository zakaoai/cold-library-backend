package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.DayOfWeek

@Table(value = "\"AnimeTorrent\"", schema = "cold_library")
data class AnimeTorrent(
    @Id
    val malId: Long,
    val lastEpisodeOnServer: Int,
    val searchWords: String,
    val dayOfRelease: DayOfWeek,
    val deltaEpisode: Int,
    val torrentPath: String,
    @kotlin.jvm.Transient @Transient
    @Value("null")
    val isNew: Boolean? = null
) : Persistable<Long> {


    override fun getId(): Long = this.malId

    override fun isNew(): Boolean = this.isNew ?: false
}