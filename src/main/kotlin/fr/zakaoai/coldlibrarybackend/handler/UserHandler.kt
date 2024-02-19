package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UserHandler(val userService: UserService) : HandlerUtils() {
    fun getCurrentUser(req: ServerRequest): Mono<ServerResponse> =
        userService.getCurrentUser()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.USER_CURRENT.message
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun updateCurrentUserMalUsername(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono(String::class.java)
            .flatMap(userService::updateCurrentUserMalUserName)
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.USER_UPDATE_CURRENT_MAL_USERNAME.message
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun getAllUser(req: ServerRequest): Mono<ServerResponse> =
        userService.getAllUser()
            .collectList()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.USER_GET_ALL.message
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)
}