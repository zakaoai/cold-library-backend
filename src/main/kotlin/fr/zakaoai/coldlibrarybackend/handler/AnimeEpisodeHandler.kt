package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.service.AnimeEpisodeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class AnimeEpisodeHandler(val animeEpisodeService: AnimeEpisodeService) {

    private val logger = LoggerFactory.getLogger(AnimeEpisodeHandler::class.java)

    fun findByMalId(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap { animeEpisodeService.findAnimeEpisodeByAnimeId(it).collectList() }
        .flatMap(ServerResponse.ok()::bodyValue)

    fun findByMalIdAndEpisodeNumber(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { animeEpisodeService.findOrcreateAnimeEpisode(it.t1,it.t2) }
        .flatMap(ServerResponse.ok()::bodyValue)

    fun deleteByMalId(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap(animeEpisodeService::deleteEpisodeByMalId)
        .flatMap { ServerResponse.noContent().build() }

    fun deleteByMalIdAndEpisodeNumber(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { animeEpisodeService.deleteByMalIdAndEpisodeNumber(it.t1,it.t2) }
        .flatMap(ServerResponse.ok()::bodyValue)


}