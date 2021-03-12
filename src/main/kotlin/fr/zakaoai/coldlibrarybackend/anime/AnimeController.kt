package fr.zakaoai.coldlibrarybackend.anime


import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.api.JikanAPIService
import org.slf4j.LoggerFactory
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping(path = ["/anime"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AnimeController(private val animeService: AnimeService, private val jikanAPIService: JikanAPIService) {

    private val logger = LoggerFactory.getLogger(AnimeController::class.java)

    @GetMapping("/{id}")
    fun findByMalId(@PathVariable id: Int): Mono<AnimeDTO> {
        return animeService.findByMalId(id).switchIfEmpty(Mono.error(NotFoundException()))
    }


    @GetMapping("/search/{search}")
    fun searchAnime(@PathVariable search: String): Flux<AnimeDTO> {
        return animeService.searchAnime(search)
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