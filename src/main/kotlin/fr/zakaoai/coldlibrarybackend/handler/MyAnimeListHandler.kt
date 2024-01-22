package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.service.MyAnimeListService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class MyAnimeListHandler(val myAnimeListService: MyAnimeListService) {

    fun getUserAnimeList(req: ServerRequest): Mono<ServerResponse> = myAnimeListService.getUserAnimeList()
        .flatMap(ServerResponse.ok()::bodyValue)
}