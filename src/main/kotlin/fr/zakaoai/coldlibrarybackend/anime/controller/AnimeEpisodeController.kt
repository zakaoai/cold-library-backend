package fr.zakaoai.coldlibrarybackend.anime.controller

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.services.EpisodeService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux


@RestController
@RequestMapping(path = ["/anime/{id}/episodes"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AnimeEpisodeController(private val animeEpisodeService: EpisodeService) {

    @GetMapping
    fun findByMalId(@PathVariable id: Int): Flux<AnimeEpisodeDTO> {
        return animeEpisodeService.searchEpisodesByAnimeId(id)
    }

}