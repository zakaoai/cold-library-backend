package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.service.DelugeTorrentService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2

@Component
class DelugeTorrentHandler(val delugeTorrentService: DelugeTorrentService) {

    fun downloadTorrent(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono().zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { (malId, episodeNumber) -> delugeTorrentService.downloadTorrent(malId, episodeNumber) }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())

    fun updateTorrent(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono().zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { (malId, episodeNumber) -> delugeTorrentService.updateTorrent(malId, episodeNumber) }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())

    fun updateAllTorrent(req: ServerRequest): Mono<ServerResponse> =  delugeTorrentService.updateAllTorrent()
        .collectList()
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())

}