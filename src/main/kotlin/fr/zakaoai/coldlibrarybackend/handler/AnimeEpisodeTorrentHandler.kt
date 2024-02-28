package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.service.AnimeEpisodeTorrentService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AnimeEpisodeTorrentHandler(
    val animeEpisodeTorrentService: AnimeEpisodeTorrentService
) : HandlerUtils() {

    fun findByMalId(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeTorrentService.findAnimeEpisodeTorrentByMalId(malId(req))
            .collectList()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_TORRENT_FIND_BY_MAL_ID.message.format(malId(req))
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun findAllDownloading(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeTorrentService.getAllDownloadingEpisodeTorrent()
            .collectList()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_TORRENT_FIND_ALL_DOWNLOADING.message
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun searchAlternate(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeTorrentService.searchAlternateEpisodeTorrent(
            malId(req),
            episodeNumber(req)
        )
            .collectList()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_TORRENT_SEARCH_ALTERNATE.message.format(
                        malId(req),
                        episodeNumber(req)
                    )
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)


    fun update(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeTorrentService.updateEpisodeTorrent(
            malId(req),
            episodeNumber(req)
        )
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_TORRENT_UPDATE.message.format(
                        malId(req),
                        episodeNumber(req)
                    )
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)


    fun replace(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono(AnimeEpisodeTorrentDTO::class.java)
            .flatMap {
                animeEpisodeTorrentService.replaceEpisodeTorrent(
                    malId(req),
                    episodeNumber(req),
                    it
                )
            }
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_TORRENT_REPLACE.message.format(
                        malId(req),
                        episodeNumber(req)
                    )
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)


    fun delete(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeTorrentService.deleteEpisodeTorrent(
            malId(req),
            episodeNumber(req)
        )
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_TORRENT_DELETE.message.format(
                        malId(req),
                        episodeNumber(req)
                    )
                )
            }
            .flatMap { ServerResponse.ok().build() }


    fun scanAll(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeTorrentService.scanEpisodeTorrent(malId(req))
            .collectList()
            .defaultIfEmpty(emptyList<AnimeEpisodeTorrentDTO>())
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_TORRENT_SCAN_ALL.message.format(malId(req))
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)


    fun scanPackage(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeTorrentService.scanPackageTorrent(malId(req))
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_TORRENT_SCAN_PACKAGE.message.format(malId(req))
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)
            .switchIfEmpty(ServerResponse.notFound().build())


    fun scanNext(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeTorrentService.scanNextEpisode(malId(req))
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_TORRENT_SCAN_NEXT.message.format(malId(req))
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)
            .switchIfEmpty(ServerResponse.notFound().build())


}