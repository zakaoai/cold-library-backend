package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.service.AnimeEpisodeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class AnimeEpisodeHandler(val animeEpisodeService: AnimeEpisodeService) : HandlerLogger() {

    private val logger = LoggerFactory.getLogger(AnimeEpisodeHandler::class.java)

    fun findByMalId(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap { animeEpisodeService.findAnimeEpisodeByAnimeId(it).collectList() }
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_EPISODE_FIND_BY_MAL_ID.message.format(req.pathVariable("id"))
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)

    fun findByMalIdAndEpisodeNumber(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { animeEpisodeService.findOrcreateAnimeEpisode(it.t1, it.t2) }
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_EPISODE_FIND_BY_MAL_ID_AND_EPISODE_NUMBER.message.format(
                    req.pathVariable("id"),
                    req.pathVariable("episodeNumber")
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)

    fun deleteByMalId(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap(animeEpisodeService::deleteEpisodeByMalId)
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_EPISODE_DELETE_BY_MAL_ID.message.format(req.pathVariable("id"))
            )
        }
        .flatMap { ServerResponse.noContent().build() }

    fun deleteByMalIdAndEpisodeNumber(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { animeEpisodeService.deleteByMalIdAndEpisodeNumber(it.t1, it.t2) }
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.ANIME_EPISODE_DELETE_BY_MAL_ID_AND_EPISODE_NUMBER.message.format(
                    req.pathVariable("id"),
                    req.pathVariable("episodeNumber")
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)


}