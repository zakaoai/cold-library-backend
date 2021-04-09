package fr.zakaoai.coldlibrarybackend.torrent.controller

import fr.zakaoai.coldlibrarybackend.torrent.DTO.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.torrent.DTO.TrackedAnimeTorrentDTO
import fr.zakaoai.coldlibrarybackend.torrent.services.TorrentService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/torrent"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TorrentController(private val torrentService: TorrentService) {

    @GetMapping("{id}")
    fun getTrackedAnime(@PathVariable id: Int): Mono<TrackedAnimeTorrentDTO> {
        return Mono.empty();
    }

    @GetMapping("{id}/episodes")
    fun getAnimeEpisodeTorrents(@PathVariable id: Int): Flux<AnimeEpisodeTorrentDTO> {
        return Flux.empty();
    }

    @GetMapping
    fun getAllTrackedAnime(): Flux<TrackedAnimeTorrentDTO> {
        return Flux.empty();
    }

    @GetMapping("{id}/episodes/{episodeNumber}/alternate")
    fun searchAlternateEpisodeTorrent(
        @PathVariable id: Int,
        @PathVariable episodeNumber: Int
    ): Flux<AnimeEpisodeTorrentDTO> {
        return Flux.empty();
    }

    @PutMapping("{id}/episodes/{episodeNumber}")
    fun replaceEpisodeTorrent(
        @PathVariable id: Int,
        @PathVariable episodeNumber: Int,
        @RequestBody animeEpisodeTorrent: AnimeEpisodeTorrentDTO
    ): Mono<AnimeEpisodeTorrentDTO> {
        return Mono.empty();
    }

    @PostMapping("{id}")
    fun createTrackedAnime(@PathVariable id: Int): Mono<TrackedAnimeTorrentDTO> {
        return Mono.empty();
    }
}
