package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeTorrent
import fr.zakaoai.coldlibrarybackend.service.AnimeTorrentService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AnimeTorrentHandler(val animeTorrentService: AnimeTorrentService) : HandlerUtils() {


    fun getTrackedAnime(req: ServerRequest): Mono<ServerResponse> = animeTorrentService.getTrackedAnime(malId(req))
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_TORRENT_GET_BY_MAL_ID.message.format(
                    malId(req)
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun getAllTrackedAnime(req: ServerRequest): Mono<ServerResponse> = animeTorrentService.getAllTrackedAnime()
        .collectList()
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_TORRENT_GET_ALL.message
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)


    fun updateTrackedAnime(req: ServerRequest): Mono<ServerResponse> = req.bodyToMono(AnimeTorrent::class.java)
        .map { it.copy(malId = malId(req)) }
        .flatMap(animeTorrentService::updateTrackedAnime)
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_TORRENT_UPDATE_BY_MAL_ID.message.format(
                    malId(req)
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun deleteTrackedAnime(req: ServerRequest): Mono<ServerResponse> =
        animeTorrentService.deleteTrackedAnime(malId(req))
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_TORRENT_DELETE_BY_MAL_ID.message.format(
                        malId(req)
                    )
                )
            }
            .flatMap { ServerResponse.noContent().build() }


    fun createTrackedAnime(req: ServerRequest): Mono<ServerResponse> =
        animeTorrentService.createTrackedAnime(malId(req))
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_TORRENT_CREATE_BY_MAL_ID.message.format(
                        malId(req)
                    )
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)
            .switchIfEmpty(ServerResponse.notFound().build())


}