package fr.zakaoai.coldlibrarybackend.anime.enums

enum class RequestStatus(val state:String) {
    PENDING("PENDING"),
    REJECTED("REJECTED"),
    ACCEPTED("ACCEPTED"),
}