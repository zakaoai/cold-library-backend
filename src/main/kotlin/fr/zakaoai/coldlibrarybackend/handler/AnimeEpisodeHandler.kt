package fr.zakaoai.coldlibrarybackend.handler

//import fr.zakaoai.coldlibrarybackend.service.EpisodeService
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Component
//import org.springframework.web.bind.annotation.DeleteMapping
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.reactive.function.server.ServerRequest
//import org.springframework.web.reactive.function.server.ServerResponse
//import reactor.core.publisher.Mono
//import reactor.kotlin.core.publisher.toMono
//
//@Component
//class AnimeEpisodeHandler(val animeEpisodeService: EpisodeService) {
//
//    private val logger = LoggerFactory.getLogger(AnimeEpisodeHandler::class.java)
//
//    fun findByMalId(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
//        .toMono()
//        .flatMap { animeEpisodeService.searchEpisodesByAnimeId(it).collectList() }
//        .flatMap(ServerResponse.ok()::bodyValue)
//
//    fun deleteByMalId(req: ServerRequest): Mono<ServerResponse> = req.pathVariable("id").toInt()
//        .toMono()
//        .flatMap(animeEpisodeService::removeEpisodesByAnimeId)
//        .flatMap{ServerResponse.noContent().build()}
//
//
//}