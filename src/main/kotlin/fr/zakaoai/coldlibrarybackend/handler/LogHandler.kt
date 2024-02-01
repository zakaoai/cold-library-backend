package fr.zakaoai.coldlibrarybackend.handler


import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class LogHandler : HandlerUtils() {

    fun getAllLogs(req: ServerRequest): Mono<ServerResponse> = logService.getLogs()
        .collectList()
        .doOnNext { logRequest(req, LogMessageHandler.LOG_GET_ALL_LOGS.message) }
        .flatMap(ServerResponse.ok()::bodyValue)

    fun getLogsByUser(req: ServerRequest): Mono<ServerResponse> = logService.getLogOfUser(req.pathVariable("userId"))
        .collectList()
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.LOG_GET_USER_LOGS.message.format(req.pathVariable("userId"))
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
}