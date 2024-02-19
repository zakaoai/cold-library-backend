package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Log
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.User
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.LogRepository
import org.springframework.stereotype.Service

@Service
class LogService(val logRepository: LogRepository) {

    fun addLog(logMessage: String, user: User) = logRepository.save(Log(null, logMessage, null, user.userId))


    fun getLogs() = logRepository.findAllWithUserInformation()

    fun getLogOfUser(userId: String) = logRepository.findByUserId(userId)
}