package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.service.DelugeTorrentService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DelugeTorrentHandler(val delugeTorrentService: DelugeTorrentService) : HandlerUtils() {

    fun downloadTorrent(req: ServerRequest): Mono<ServerResponse> =
        delugeTorrentService.downloadTorrent(malId(req), episodeNumber(req))
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.DELUGE_TORRENT_DOWNLOAD.message.format(
                        malId(req),
                        episodeNumber(req)
                    )
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)
            .switchIfEmpty(ServerResponse.notFound().build())

    fun updateTorrent(req: ServerRequest): Mono<ServerResponse> =
        delugeTorrentService.updateTorrent(malId(req), episodeNumber(req))
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.DELUGE_TORRENT_UPDATE.message.format(
                        malId(req),
                        episodeNumber(req)
                    )
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)
            .switchIfEmpty(ServerResponse.notFound().build())

    fun updateAllTorrent(req: ServerRequest): Mono<ServerResponse> = delugeTorrentService.updateAllTorrent()
        .collectList()
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.DELUGE_TORRENT_UPDATE_ALL.message
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())

}