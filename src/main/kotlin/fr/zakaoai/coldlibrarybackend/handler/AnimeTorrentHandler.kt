package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeTorrent
import fr.zakaoai.coldlibrarybackend.service.AnimeTorrentService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class AnimeTorrentHandler(val animeTorrentService: AnimeTorrentService) {


    fun getTrackedAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap(animeTorrentService::getTrackedAnime)
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun getAllTrackedAnime(req: ServerRequest): Mono<ServerResponse> = animeTorrentService.getAllTrackedAnime()
        .collectList()
        .flatMap(ServerResponse.ok()::bodyValue)


    fun updateTrackedAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono().zipWith(req.bodyToMono(AnimeTorrent::class.java))
        .flatMap { animeTorrentService.updateTrackedAnime(it.t2.copy(malId = it.t1)) }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun deleteTrackedAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap(animeTorrentService::deleteTrackedAnime)
        .flatMap { ServerResponse.noContent().build() }


    fun createTrackedAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap(animeTorrentService::createTrackedAnime)
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


}