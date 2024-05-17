package fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist

enum class AnimeListStatus(val value: String) {
    WATCHING("watching"),
    COMPLETED("completed"),
    ON_HOLD("on_hold"),
    DROPPED("dropped"),
    PLAN_TO_WATCH("plan_to_watch")
}