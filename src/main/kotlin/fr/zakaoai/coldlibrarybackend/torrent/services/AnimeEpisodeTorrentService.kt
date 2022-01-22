package fr.zakaoai.coldlibrarybackend.torrent.services

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.services.EpisodeService
import fr.zakaoai.coldlibrarybackend.torrent.DTO.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.torrent.api.NyaaTorrentService
import fr.zakaoai.coldlibrarybackend.torrent.repository.AnimeEpisodeTorrentRepository
import fr.zakaoai.coldlibrarybackend.torrent.repository.TrackedAnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.AnimeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.TrackedAnimeTorrent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class AnimeEpisodeTorrentService(
    private val animeEpisodeTorrentRepository: AnimeEpisodeTorrentRepository,
    private val trackedAnimeTorrentRepository: TrackedAnimeTorrentRepository,
    private val episodeService: EpisodeService,
    private val nyaaTorrentService: NyaaTorrentService
) {

    private val logger = LoggerFactory.getLogger(AnimeEpisodeTorrentService::class.java)


    fun findAnimeEpisodeTorrentByMalId(malId: Int): Flux<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentRepository.findByMalId(malId)
            .map(AnimeEpisodeTorrent::toAnimeEpisodeTorrentDTO)
    }

    fun searchAlternateEpisodeTorrent(malId: Int, episodeNumber: Int): Flux<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .map(AnimeEpisodeTorrent::torrentId)
            .flatMapMany { torrentId ->
                nyaaTorrentService.searchEpisodeTorrent(malId, episodeNumber)
                    .filter { episodeTorrent -> episodeTorrent.torrentId != torrentId }
            }
    }

    fun replaceEpisodeTorrent(
        malId: Int,
        episodeNumber: Int,
        animeEpisodeTorrent: AnimeEpisodeTorrentDTO
    ): Mono<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .map { animeEpisodeTorrent.toModel(it.id) }
            .flatMap(animeEpisodeTorrentRepository::save)
            .map(AnimeEpisodeTorrent::toAnimeEpisodeTorrentDTO)
    }

    fun isSameEpisodeNumber(animeEpisode: AnimeEpisodeDTO, episodeNumber: Int): Boolean {
        return animeEpisode.episodeNumber == episodeNumber
    }

    fun isAnimeEpisodeInEpisodeNumbers(animeEpisode: AnimeEpisodeDTO, episodeNumbers: List<Int>): Boolean {
        return episodeNumbers.any { episodeNumber -> isSameEpisodeNumber(animeEpisode, episodeNumber) }
    }

    fun filterAnimeEpisodeList(malId: Int, animeEpisodeList: List<AnimeEpisodeDTO>): Flux<AnimeEpisodeDTO> {
        return animeEpisodeTorrentRepository.findByMalId(malId)
            .map(AnimeEpisodeTorrent::episodeNumber)
            .collectList()
            .flatMapMany { episodeNumbers ->
                animeEpisodeList.filter { animeEpisode ->
                    !isAnimeEpisodeInEpisodeNumbers(
                        animeEpisode,
                        episodeNumbers
                    )
                }.toFlux()
            }
    }


    fun scanEpisodeTorrent(malId: Int): Flux<AnimeEpisodeTorrentDTO> {
        return trackedAnimeTorrentRepository.findByMalId(malId)
            .map(TrackedAnimeTorrent::lastEpisodeOnServer)
            .flatMapMany { lastAvaible ->
                episodeService.searchEpisodesByAnimeId(malId)
                    .filter { ep -> ep.episodeNumber > lastAvaible }
            }
            .collectList()
            .flatMapMany { animeList -> filterAnimeEpisodeList(malId, animeList) }
            .flatMap { nyaaTorrentService.searchEpisodeTorrent(malId, it.episodeNumber).next() }
            .flatMap(this::saveAnimeTorrent)
    }

    fun scanNextEpisode(malId: Int): Mono<AnimeEpisodeTorrentDTO> {
        return trackedAnimeTorrentRepository.findByMalId(malId)
            .map(TrackedAnimeTorrent::lastEpisodeOnServer)
            .flatMap { lastAvaible ->
                episodeService.searchEpisodeByAnimeIdAndEpisodeNumber(malId, lastAvaible + 1)
            }
            .flatMap { scanTorrentByMalIdAndEpisodeNumber(malId, it.episodeNumber) }
    }

    fun scanPackageTorrent(malId: Int): Mono<AnimeEpisodeTorrentDTO> {
        return scanTorrentByMalIdAndEpisodeNumber(malId, 0)
    }

    fun scanTorrentByMalIdAndEpisodeNumber(malId: Int, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO> {
        return nyaaTorrentService.searchEpisodeTorrent(malId, episodeNumber).next()
            .flatMap(this::saveAnimeTorrent)
    }

    fun saveAnimeTorrent(torrent: AnimeEpisodeTorrentDTO): Mono<AnimeEpisodeTorrentDTO> {
        return Mono.just(torrent).map(AnimeEpisodeTorrentDTO::toModel)
            .flatMap(animeEpisodeTorrentRepository::save)
            .map(AnimeEpisodeTorrent::toAnimeEpisodeTorrentDTO)
    }

}