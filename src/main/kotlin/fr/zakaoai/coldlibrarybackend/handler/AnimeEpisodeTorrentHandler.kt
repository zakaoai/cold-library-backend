package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.infrastructure.NyaaTorrentService
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.service.AnimeEpisodeTorrentService
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class AnimeEpisodeTorrentHandler(
    val animeEpisodeTorrentService: AnimeEpisodeTorrentService,
    val nyaaTorrentService: NyaaTorrentService
) {

    fun getAnimeEpisodeTorrents(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono()
        .flatMap { animeEpisodeTorrentService.findAnimeEpisodeTorrentByMalId(it).collectList() }
        .flatMap(ServerResponse.ok()::bodyValue)



    fun searchAlternateEpisodeTorrent(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono().zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { animeEpisodeTorrentService.searchAlternateEpisodeTorrent(it.t1, it.t2).collectList() }
        .flatMap(ServerResponse.ok()::bodyValue)



    fun updateEpisodeTorrent(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono().zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { animeEpisodeTorrentService.updateEpisodeTorrent(it.t1, it.t2) }
        .flatMap(ServerResponse.ok()::bodyValue)



    fun searchEpisodeTorrent(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono().zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { nyaaTorrentService.searchEpisodeTorrent(it.t1, it.t2).collectList() }
        .flatMap(ServerResponse.ok()::bodyValue)



    fun replaceEpisodeTorrent(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono(AnimeEpisodeTorrentDTO::class.java)
            .flatMap {
                animeEpisodeTorrentService.replaceEpisodeTorrent(
                    req.pathVariable("id").toInt(),
                    req.pathVariable("episodeNumber").toInt(),
                    it
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)



    fun deleteEpisodeTorrent(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono().zipWith(req.pathVariable("episodeNumber").toInt().toMono())
        .flatMap { animeEpisodeTorrentService.deleteEpisodeTorrent(it.t1, it.t2) }
        .flatMap { ServerResponse.ok().build() }



    fun scanEpisodeTorrent(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono()
        .flatMap { animeEpisodeTorrentService.scanEpisodeTorrent(it).collectList() }
        .defaultIfEmpty(emptyList<AnimeEpisodeTorrentDTO>())
        .flatMap(ServerResponse.ok()::bodyValue)



    fun scanPackageTorrent(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono()
        .flatMap(animeEpisodeTorrentService::scanPackageTorrent)
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())



    fun scanNextTorrent(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
        .toMono()
        .flatMap(animeEpisodeTorrentService::scanNextEpisode)
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound().build())


}