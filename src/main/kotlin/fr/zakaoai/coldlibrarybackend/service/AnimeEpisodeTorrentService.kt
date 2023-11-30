package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.NyaaTorrentService
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.TrackedAnimeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeEpisodeTorrentRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.TrackedAnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
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

            .flatMapMany { et ->
                nyaaTorrentService.searchEpisodeTorrent(malId, episodeNumber)
                    .handle { t, u ->
                        if (t.torrentId == et.torrentId) saveAnimeTorrentWithId(t, et.id).subscribe()
                        u.next(t)
                    }

            }
    }

    fun updateEpisodeTorrent(malId: Int, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .flatMap { fromRepo ->
                nyaaTorrentService.searchEpisodeTorrentById(fromRepo.torrentId, malId, episodeNumber)
                    .flatMap { saveAnimeTorrentWithId(it, fromRepo.id) }
            }
    }

    fun replaceEpisodeTorrent(
        malId: Int,
        episodeNumber: Int,
        animeEpisodeTorrent: AnimeEpisodeTorrentDTO
    ): Mono<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .flatMap { saveAnimeTorrentWithId(animeEpisodeTorrent, it.id) }
    }

    fun deleteEpisodeTorrent(malId: Int, episodeNumber: Int): Mono<Void> {
        return animeEpisodeTorrentRepository.deleteByMalIdAndEpisodeNumber(malId, episodeNumber);
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
        return animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber).singleOptional()
            .flatMap {
                if (it.isEmpty)
                    nyaaTorrentService.searchEpisodeTorrent(malId, episodeNumber).next()
                        .flatMap(this::saveAnimeTorrent)
                else
                    Mono.empty()
            }
    }

    fun saveAnimeTorrentWithId(torrent: AnimeEpisodeTorrentDTO, id: Long?): Mono<AnimeEpisodeTorrentDTO> {
        return Mono.just(torrent)
            .map { it.toModel(id) }
            .flatMap(animeEpisodeTorrentRepository::save)
            .map(AnimeEpisodeTorrent::toAnimeEpisodeTorrentDTO)
    }

    fun saveAnimeTorrent(torrent: AnimeEpisodeTorrentDTO): Mono<AnimeEpisodeTorrentDTO> {
        return saveAnimeTorrentWithId(torrent, null)
    }

}