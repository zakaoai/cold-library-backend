package fr.zakaoai.coldlibrarybackend.anime.controller

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.services.EpisodeService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping(path = ["/anime/{id}/episodes"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AnimeEpisodeController(private val animeEpisodeService: EpisodeService) {

    @GetMapping
    fun findByMalId(@PathVariable id: Int): Flux<AnimeEpisodeDTO> {
        return animeEpisodeService.searchEpisodesByAnimeId(id)
    }

    @DeleteMapping
    fun deleteByMalId(@PathVariable id: Int): Mono<Void> {
        return animeEpisodeService.removeEpisodesByAnimeId(id)
    }

}