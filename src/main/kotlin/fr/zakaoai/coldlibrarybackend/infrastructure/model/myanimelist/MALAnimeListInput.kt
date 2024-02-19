package fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist

data class MALAnimeListInput(
    val status: String? = null,
    val sort: String? = null,
    val limit: Int = 100,
    val offset: Int = 0
)
