package fr.zakaoai.coldlibrarybackend.enums

enum class LogMessageHandler(val message: String) {
    LOG_GET_ALL_LOGS("Lecture des logs"),
    LOG_GET_USER_LOGS("Lecture des logs de l'utilisateur %s")
}