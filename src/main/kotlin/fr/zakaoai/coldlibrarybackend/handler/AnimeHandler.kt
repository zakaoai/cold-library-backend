package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.service.AnimeService
import net.sandrohc.jikan.model.season.Season
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class AnimeHandler(val animeService: AnimeService) : HandlerUtils() {

    private val logger = LoggerFactory.getLogger(AnimeHandler::class.java)

    fun getAllAnime(req: ServerRequest): Mono<ServerResponse> = animeService.getAllAnime()
        .collectList()
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_GET_ALL.message
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)


    fun findByMalId(req: ServerRequest): Mono<ServerResponse> = animeService.findByMalId(malId(req))
        .flatMap(ServerResponse.ok()::bodyValue)
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_FIND_BY_MAL_ID.message.format(
                    malId(req)
                )
            )
        }
        .switchIfEmpty(ServerResponse.notFound().build())


    fun updateByMalId(req: ServerRequest): Mono<ServerResponse> = animeService.updateAnimeAndSave(malId(req))
        .flatMap(ServerResponse.ok()::bodyValue)
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_UPDATE_BY_MAL_ID.message.format(
                    malId(req)
                )
            )
        }
        .switchIfEmpty(ServerResponse.notFound().build())


    fun saveAnime(req: ServerRequest): Mono<ServerResponse> = animeService.findAnimeInServerAndSave(malId(req))
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_SAVE.message.format(
                    malId(req)
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)


    fun deleteAnime(req: ServerRequest): Mono<ServerResponse> = animeService.deleteById(malId(req))
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_DELETE.message.format(
                    malId(req)
                )
            )
        }
        .flatMap { ServerResponse.noContent().build() }


    fun searchAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("search").takeIf { it.length >= 3 }
        .toMono()
        .flatMapMany(animeService::searchAnime)
        .collectList()
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_SEARCH.message.format(
                    req.pathVariable("search")
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.badRequest().build())


    fun updateStorageState(req: ServerRequest): Mono<ServerResponse> = req.bodyToMono(String::class.java)
        .flatMap { animeService.updateAnimeStorageState(malId(req), it) }
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_UPDATE_STORAGE_STATE.message.format(
                    malId(req)
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun updateLastAvaibleEpisode(req: ServerRequest): Mono<ServerResponse> = req.bodyToMono(Int::class.java)
        .flatMap { animeService.updateAnimeLastAvaibleEpisode(malId(req), it) }
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_UPDATE_LAST_AVAIBLE_EPISODE.message.format(
                    malId(req)
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun updateIsComplete(req: ServerRequest): Mono<ServerResponse> = req.bodyToMono(Boolean::class.java)
        .flatMap { animeService.updateAnimeIsComplete(malId(req), it) }
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_UPDATE_IS_COMPLETE.message.format(
                    malId(req)
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())

    fun updateIsDownloading(req: ServerRequest): Mono<ServerResponse> = req.bodyToMono(Boolean::class.java)
        .flatMap { animeService.updateIsDownloading(malId(req), it) }
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_UPDATE_IS_DOWNLOADING.message.format(
                    malId(req)
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())

    fun searchAnimeBySeason(req: ServerRequest): Mono<ServerResponse> =
        animeService.searchAnimeBySeason(
            req.pathVariable("year").toInt(),
            Season.valueOf(req.pathVariable("season")),
            req.pathVariable("page").toInt()
        )
            .collectList()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_SEARCH_BY_SEASON.message.format(
                        req.pathVariable("year").toInt(),
                        Season.valueOf(req.pathVariable("season")),
                        req.pathVariable("page").toInt()
                    )
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)
            .switchIfEmpty(ServerResponse.notFound().build())

    fun getSeasons(req: ServerRequest): Mono<ServerResponse> = animeService.getSeasons()
        .collectList()
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_GET_SEASONS.message
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())
}