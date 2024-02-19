package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.service.AnimeEpisodeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AnimeEpisodeHandler(val animeEpisodeService: AnimeEpisodeService) : HandlerUtils() {

    private val logger = LoggerFactory.getLogger(AnimeEpisodeHandler::class.java)

    fun findByMalId(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeService.findAnimeEpisodeByAnimeId(malId(req))
            .collectList()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_FIND_BY_MAL_ID.message.format(malId(req))
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun findByMalIdAndEpisodeNumber(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeService.findOrcreateAnimeEpisode(
            malId(req),
            episodeNumber(req)
        )
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_FIND_BY_MAL_ID_AND_EPISODE_NUMBER.message.format(
                        malId(req),
                        episodeNumber(req)
                    )
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun deleteByMalId(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeService.deleteEpisodeByMalId(malId(req))
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_DELETE_BY_MAL_ID.message.format(malId(req))
                )
            }
            .flatMap { ServerResponse.noContent().build() }

    fun deleteByMalIdAndEpisodeNumber(req: ServerRequest): Mono<ServerResponse> =
        animeEpisodeService.deleteByMalIdAndEpisodeNumber(
            malId(req),
            episodeNumber(req)
        )
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.ANIME_EPISODE_DELETE_BY_MAL_ID_AND_EPISODE_NUMBER.message.format(
                        malId(req),
                        episodeNumber(req)
                    )
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

}