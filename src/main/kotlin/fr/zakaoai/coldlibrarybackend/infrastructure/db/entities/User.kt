package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

@Table("\"User\"")
data class User(
    @Id
    val userId: String,
    val name: String,
    val email: String,
    val malUsername: String? = null,
    @kotlin.jvm.Transient @Transient
    @Value("null")
    val isNew: Boolean? = null
) : Persistable<String> {

    override fun getId(): String = this.userId

    override fun isNew(): Boolean = this.isNew ?: false
}
