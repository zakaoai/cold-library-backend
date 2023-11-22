package fr.zakaoai.coldlibrarybackend.enums

enum class RequestStatus(val state: String) {
    PENDING("PENDING"),
    REJECTED("REJECTED"),
    ACCEPTED("ACCEPTED"),
}