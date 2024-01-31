package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UserHandler(val userService: UserService) {
    fun getCurrentUser(req: ServerRequest): Mono<ServerResponse> =
        userService.getCurrentUser()
            .flatMap(ServerResponse.ok()::bodyValue)

    fun updateCurrentUserMalUsername(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono(String::class.java)
            .flatMap(userService::updateCurrentUserMalUserName)
            .flatMap(ServerResponse.ok()::bodyValue)

    fun getAllUser(req: ServerRequest): Mono<ServerResponse> =
        userService.getAllUser()
            .collectList()
            .flatMap(ServerResponse.ok()::bodyValue)
}