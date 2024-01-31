package fr.zakaoai.coldlibrarybackend.handler


import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.User
import fr.zakaoai.coldlibrarybackend.service.LogService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class LogHandler(val logService: LogService) {

    fun getAllLogs(req: ServerRequest): Mono<ServerResponse> = logService.getLogs()
        .collectList()
        .doOnNext {
            req.session().map { it.getAttribute<User>("user") }
                .flatMap { logService.addLog(LogMessageHandler.LOG_GET_ALL_LOGS.message, it!!) }
                .subscribe()
        }
        .flatMap(ServerResponse.ok()::bodyValue)

    fun getLogsByUser(req: ServerRequest): Mono<ServerResponse> =

        logService.getLogOfUser(req.pathVariable("userId"))
        .collectList()
        .doOnNext {
            req.session().map { it.getAttribute<User>("user") }
                .flatMap { logService.addLog(LogMessageHandler.LOG_GET_USER_LOGS.message.format(req.pathVariable("userId")), it!!) }
                .subscribe()
        }
        .flatMap(ServerResponse.ok()::bodyValue)
}