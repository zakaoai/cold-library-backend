package fr.zakaoai.coldlibrarybackend.infrastructure.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("\"User\"")
data class User(
    @Id
    val id: Long? = null,
    val sub: String,
    val name: String,
    val username: String,
    val email: String,
    @Column("myanimelist_username")
    val myanimelistUsername: String,
)
