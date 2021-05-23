package fr.zakaoai.coldlibrarybackend.torrent.services

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.controller.AnimeController
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeEpisodeRepository
import fr.zakaoai.coldlibrarybackend.anime.services.EpisodeService
import fr.zakaoai.coldlibrarybackend.torrent.DTO.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.torrent.api.NyaaTorrentService
import fr.zakaoai.coldlibrarybackend.torrent.repository.AnimeEpisodeTorrentRepository
import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.AnimeEpisodeTorrent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toFlux
import java.time.Duration
import java.time.temporal.TemporalUnit

@Service
class AnimeEpisodeTorrentService(
    private val animeEpisodeTorrentRepository: AnimeEpisodeTorrentRepository,
    private val animeEpisodeRepository: AnimeEpisodeRepository,
    private val episodeService: EpisodeService,
    private val nyaaTorrentService: NyaaTorrentService
) {

    private val logger = LoggerFactory.getLogger(AnimeEpisodeTorrentService::class.java)


    fun getAnimeEpisodeTorrents(malId: Int): Flux<AnimeEpisodeTorrentDTO> {
        return episodeService.removeOldEpisodeByAnimeId(malId)
            .then(Mono.just(1))
            .flatMapMany {
            animeEpisodeTorrentRepository.findByMalId(malId)
                .map(AnimeEpisodeTorrent::toAnimeEpisodeTorrentDTO)
        }
            .timeout(Duration.ofSeconds(10));
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
        return episodeService.searchEpisodesByAnimeId(malId)
            .collectList()
            .flatMapMany { animeList -> filterAnimeEpisodeList(malId, animeList) }
            .flatMap { nyaaTorrentService.searchEpisodeTorrent(malId, it.episodeNumber).next() }
            .map(AnimeEpisodeTorrentDTO::toModel)
            .flatMap(animeEpisodeTorrentRepository::save)
            .map(AnimeEpisodeTorrent::toAnimeEpisodeTorrentDTO)
    }

}