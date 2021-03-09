package fr.zakaoai.coldlibrarybackend.anime


import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.api.JikanAPIService
import net.sandrohc.jikan.Jikan
import net.sandrohc.jikan.model.anime.Anime
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping(path = ["/anime"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AnimeController(private val animeService: AnimeService, private val jikanAPIService: JikanAPIService) {

    private val logger = LoggerFactory.getLogger(AnimeController::class.java)

    @GetMapping("/search/{search}")
    fun searchAnime(@PathVariable search: String): Flux<AnimeDTO> {
        return jikanAPIService.searchAnime(search)
    }

    @GetMapping("save/{id}")
    fun saveAnime(@PathVariable id: Int): Mono<AnimeDTO> {
        return animeService.saveAnimeById(id)
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAnime(@PathVariable id: Int): Mono<Void> {
        return animeService.deleteById(id);
    }


}