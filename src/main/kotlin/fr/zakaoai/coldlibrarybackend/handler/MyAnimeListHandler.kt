package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListNode
import fr.zakaoai.coldlibrarybackend.service.MyAnimeListService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class MyAnimeListHandler(val myAnimeListService: MyAnimeListService) : HandlerUtils() {

    fun getUserAnimeList(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok().body(myAnimeListService.getUserAnimeList()
            .doOnComplete {
                logRequest(
                    req,
                    LogMessageHandler.MY_ANIME_LIST.message
                )
            }, MALAnimeListNode::class.java
        )

}