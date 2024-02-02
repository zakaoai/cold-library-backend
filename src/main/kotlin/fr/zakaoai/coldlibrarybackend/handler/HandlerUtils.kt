package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.User
import fr.zakaoai.coldlibrarybackend.service.LogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class HandlerUtils {

    @Autowired
    lateinit var logService: LogService

    fun logRequest(req: ServerRequest, message: String) =
        req.session()
            .mapNotNull { it.getAttribute<User>("user") }
            .flatMap { logService.addLog(message, it!!) }
            .subscribe()

    fun malId(req: ServerRequest) = req.pathVariable("malId").toLong()
    fun episodeNumber(req: ServerRequest) = req.pathVariable("episodeNumber").toInt()
}