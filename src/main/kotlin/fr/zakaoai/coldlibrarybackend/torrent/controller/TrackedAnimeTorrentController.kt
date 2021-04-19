package fr.zakaoai.coldlibrarybackend.torrent.controller

import fr.zakaoai.coldlibrarybackend.torrent.DTO.TrackedAnimeTorrentDTO
import fr.zakaoai.coldlibrarybackend.torrent.services.TrackedAnimeTorrentService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/torrent"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TrackedAnimeTorrentController(private val trackedAnimeTorrentService: TrackedAnimeTorrentService) {

    @GetMapping("{id}")
    fun getTrackedAnime(@PathVariable id: Int): Mono<TrackedAnimeTorrentDTO> {
        return trackedAnimeTorrentService.getTrackedAnime(id)
    }

    @GetMapping
    fun getAllTrackedAnime(): Flux<TrackedAnimeTorrentDTO> {
        return trackedAnimeTorrentService.getAllTrackedAnime()
    }

    @PatchMapping("{id}")
    fun updateTrackedAnime(
        @PathVariable id: Int,
        @RequestBody trackedAnimeTorrentDTO: TrackedAnimeTorrentDTO
    ): Mono<TrackedAnimeTorrentDTO> {
        return trackedAnimeTorrentService.updateTrackedAnime(trackedAnimeTorrentDTO)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTrackedAnime(@PathVariable id: Int): Mono<Void> {
        return trackedAnimeTorrentService.deleteTrackedAnime(id)
    }

    @PostMapping("{id}")
    fun createTrackedAnime(@PathVariable id: Int): Mono<TrackedAnimeTorrentDTO> {
        return trackedAnimeTorrentService.createTrackedAnime(id)
    }
}
