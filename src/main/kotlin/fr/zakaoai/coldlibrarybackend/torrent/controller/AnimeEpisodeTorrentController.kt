package fr.zakaoai.coldlibrarybackend.torrent.controller

import fr.zakaoai.coldlibrarybackend.torrent.DTO.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.torrent.api.NyaaTorrentService
import fr.zakaoai.coldlibrarybackend.torrent.services.AnimeEpisodeTorrentService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/torrent/{id}/episodes"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AnimeEpisodeTorrentController(private val animeEpisodeTorrentService: AnimeEpisodeTorrentService,
                                    private val nyaaTorrentService: NyaaTorrentService) {

    @GetMapping
    fun getAnimeEpisodeTorrents(@PathVariable id: Int): Flux<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentService.findAnimeEpisodeTorrentByMalId(id)
    }

    @GetMapping("{episodeNumber}/alternate")
    fun searchAlternateEpisodeTorrent(
            @PathVariable id: Int,
            @PathVariable episodeNumber: Int
    ): Flux<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentService.searchAlternateEpisodeTorrent(id, episodeNumber)
    }

    @GetMapping("{episodeNumber}/search")
    fun searchEpisodeTorrent(
            @PathVariable id: Int,
            @PathVariable episodeNumber: Int
    ): Flux<AnimeEpisodeTorrentDTO> {
        return nyaaTorrentService.searchEpisodeTorrent(id, episodeNumber)
    }

    @PutMapping("{episodeNumber}")
    fun replaceEpisodeTorrent(
            @PathVariable id: Int,
            @PathVariable episodeNumber: Int,
            @RequestBody animeEpisodeTorrent: AnimeEpisodeTorrentDTO
    ): Mono<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentService.replaceEpisodeTorrent(id, episodeNumber, animeEpisodeTorrent)
    }

    @GetMapping("/scan")
    fun scanEpisodeTorrent(@PathVariable id: Int): Flux<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentService.scanEpisodeTorrent(id)
    }

    @GetMapping("/scanPack")
    fun scanPackageTorrent(@PathVariable id: Int): Mono<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentService.scanPackageTorrent(id)
    }

    @GetMapping("/scanNext")
    fun scanNExtTorrent(@PathVariable id: Int): Mono<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentService.scanNextEpisode(id)
    }
}