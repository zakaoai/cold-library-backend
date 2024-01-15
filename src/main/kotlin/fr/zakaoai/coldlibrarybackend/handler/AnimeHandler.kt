package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.service.AnimeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono

@Component
class AnimeHandler(val animeService: AnimeService) {

    private val logger = LoggerFactory.getLogger(AnimeHandler::class.java)

    fun getAllAnime(req: ServerRequest): Mono<ServerResponse> =
        animeService.getAllAnime().collectList().flatMap(ServerResponse.ok()::bodyValue)


    fun findByMalId(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap(animeService::findByMalId)
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun updateByMalId(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap(animeService::updateAnimeAndSave)
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun saveAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap(animeService::findAnimeInServerAndSave)
        .flatMap(ServerResponse.ok()::bodyValue)


    fun deleteAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono()
        .flatMap(animeService::deleteById)
        .flatMap{ServerResponse.noContent().build()}


    fun searchAnime(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("search").takeIf { it.length >= 3 }
        .toMono()
        .flatMap{animeService.searchAnime(it).collectList()}
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.badRequest().build())


    fun updateStorageState(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono().zipWith(req.bodyToMono(String::class.java))
        .flatMap { animeService.updateAnimeStorageState(it.t1, it.t2) }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun updateLastAvaibleEpisode(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono().zipWith(req.bodyToMono(Int::class.java))
        .flatMap { animeService.updateAnimeLastAvaibleEpisode(it.t1, it.t2) }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


    fun updateIsComplete(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono().zipWith(req.bodyToMono(Boolean::class.java))
        .flatMap { animeService.updateAnimeIsComplete(it.t1, it.t2) }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())

    fun updateIsDownloading(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toLong()
        .toMono().zipWith(req.bodyToMono(Boolean::class.java))
        .flatMap { animeService.updateIsDownloading(it.t1, it.t2) }
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())
}