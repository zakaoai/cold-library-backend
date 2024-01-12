package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import fr.zakaoai.coldlibrarybackend.enums.StorageState
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(value = "\"AnimeInServer\"", schema = "cold_library")
data class AnimeInServer(
    @Id
    val malId: Long,
    @Column("storage_state")
    val storageState: StorageState,
    val isDownloading: Boolean,
    val isComplete: Boolean,
    val lastAvaibleEpisode: Int,
    @CreatedDate
    val addedOnServer: LocalDateTime? = LocalDateTime.now(),
    @kotlin.jvm.Transient @Transient
    @Value("null")
    val isNew: Boolean? = null
) : Persistable<Long> {
    override fun getId(): Long = this.malId

    override fun isNew(): Boolean = this.isNew ?: false
}
