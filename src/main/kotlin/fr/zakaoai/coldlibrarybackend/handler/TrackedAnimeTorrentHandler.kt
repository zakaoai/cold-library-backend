package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.model.dto.response.TrackedAnimeTorrentDTO
import fr.zakaoai.coldlibrarybackend.service.TrackedAnimeTorrentService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class TrackedAnimeTorrentHandler(val trackedAnimeTorrentService: TrackedAnimeTorrentService) {


    fun getTrackedAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono()
        .flatMap(trackedAnimeTorrentService::getTrackedAnime)
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun getAllTrackedAnime(req: ServerRequest): Mono<ServerResponse> =
        trackedAnimeTorrentService.getAllTrackedAnime().collectList().flatMap(ServerResponse.ok()::bodyValue)


    fun updateTrackedAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono().zipWith(req.bodyToMono(TrackedAnimeTorrentDTO::class.java))
        .flatMap { trackedAnimeTorrentService.updateTrackedAnime(it.t2) }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun deleteTrackedAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono()
        .flatMap(trackedAnimeTorrentService::deleteTrackedAnime)
        .flatMap { ServerResponse.noContent().build() }


    fun createTrackedAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono()
        .flatMap(trackedAnimeTorrentService::createTrackedAnime)
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


}