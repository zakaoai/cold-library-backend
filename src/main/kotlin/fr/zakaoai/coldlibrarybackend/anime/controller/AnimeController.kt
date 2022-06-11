package fr.zakaoai.coldlibrarybackend.anime.controller


import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.services.AnimeService
import org.slf4j.LoggerFactory
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.constraints.Size


@RestController
@RequestMapping(path = ["/anime"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class AnimeController(private val animeService: AnimeService) {

    private val logger = LoggerFactory.getLogger(AnimeController::class.java)

    @GetMapping
    fun getAllAnime(): Flux<AnimeDTO> {
        return animeService.getAllAnime()
    }

    @GetMapping("{id}")
    fun findByMalId(@PathVariable id: Int): Mono<AnimeDTO> {
        return animeService.findByMalId(id).switchIfEmpty(Mono.error(NotFoundException()))
    }

    @GetMapping("{id}/update")
    fun updateByMalId(@PathVariable id: Int): Mono<AnimeDTO> {
        return animeService.updateAnimeAndSave(id).switchIfEmpty(Mono.error(NotFoundException()))
    }

    @PostMapping("{id}")
    fun saveAnime(@PathVariable id: Int): Mono<AnimeDTO> {
        return animeService.saveAnimeById(id)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAnime(@PathVariable id: Int): Mono<Void> {
        return animeService.deleteById(id)
    }

    @GetMapping("/search/{search}")
    fun searchAnime(@PathVariable @Size(min = 3) search: String): Flux<AnimeDTO> {
        return animeService.searchAnime(search)
    }

    @PutMapping("{id}/storage_state")
    fun updateStorageState(@PathVariable id: Int, @RequestBody storageState: String): Mono<AnimeDTO> {
        return animeService.updateAnimeStorageState(id, storageState).switchIfEmpty(Mono.error(NotFoundException()))
    }

    @PutMapping("{id}/last_avaible_episode")
    fun updateLastAvaibleEpisode(@PathVariable id: Int, @RequestBody lastAvaibleEpisode: Int): Mono<AnimeDTO> {
        return animeService.updateAnimeLastAvaibleEpisode(id, lastAvaibleEpisode)
            .switchIfEmpty(Mono.error(NotFoundException()))
    }

    @PutMapping("{id}/is_complete")
    fun updateIsComplete(@PathVariable id: Int, @RequestBody isComplete: Boolean): Mono<AnimeDTO> {
        return animeService.updateAnimeIsComplete(id, isComplete).switchIfEmpty(Mono.error(NotFoundException()))
    }
}