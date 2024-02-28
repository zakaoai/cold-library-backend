package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.NyaaTorrentService
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.*
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeEpisodeTorrentDTO
import fr.zakaoai.coldlibrarybackend.model.mapper.toModel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.jvm.optionals.getOrNull

@Service
class AnimeEpisodeTorrentService(
    private val animeEpisodeTorrentRepository: AnimeEpisodeTorrentRepository,
    private val animeTorrentRepository: AnimeTorrentRepository,
    private val animeInServerRepository: AnimeInServerRepository,
    private val animeEpisodeService: AnimeEpisodeService,
    private val nyaaTorrentService: NyaaTorrentService,
    private val delugeEpisodeTorrentRepository: DelugeEpisodeTorrentRepository,
    private val animeEpisodeRepository: AnimeEpisodeRepository
) {

    private val logger = LoggerFactory.getLogger(AnimeEpisodeTorrentService::class.java)


    fun findAnimeEpisodeTorrentByMalId(malId: Long) = animeEpisodeTorrentRepository.findByMalIdWithEpisodeNumber(malId)

    fun getAllDownloadingEpisodeTorrent() = animeTorrentRepository.getAllDownloadingAnime().flatMap {
        animeEpisodeTorrentRepository.findByMalId(it.malId)
    }.flatMap { animeEpisodeTorrent ->
        delugeEpisodeTorrentRepository.findByIdAnimeEpisodeTorrent(animeEpisodeTorrent.id!!).singleOptional()
            .zipWith(animeEpisodeRepository.findById(animeEpisodeTorrent.idAnimeEpisode)).map {
                animeEpisodeTorrent.toAnimeEpisodeTorrentDTO(it.t2.episodeNumber, it.t1.getOrNull()?.progress)
            }
    }

    fun searchAlternateEpisodeTorrent(malId: Long, episodeNumber: Int): Flux<AnimeEpisodeTorrentDTO> =
        animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .flatMapMany { animeEpisodeTorrent ->
                animeTorrentRepository.findById(malId)
                    .flatMapMany {
                        nyaaTorrentService.searchEpisodeTorrent(
                            malId,
                            episodeNumber,
                            it.searchWords
                        )
                    }
                    .filter { torrentPreview -> torrentPreview.id != animeEpisodeTorrent.torrentId }
                    .map { it.toAnimeEpisodeTorrent(malId, animeEpisodeTorrent.idAnimeEpisode) }
            }
            .map { it.toAnimeEpisodeTorrentDTO(episodeNumber) }

    fun updateEpisodeTorrent(malId: Long, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO> =
        animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .flatMap { animeEpisodeTorrent ->
                nyaaTorrentService.searchEpisodeTorrentById(animeEpisodeTorrent.torrentId)
                    .map {
                        it.toAnimeEpisodeTorrent(
                            malId,
                            animeEpisodeTorrent.idAnimeEpisode,
                            animeEpisodeTorrent.torrentId
                        ).copy(id = animeEpisodeTorrent.id)
                    }
                    .flatMap(animeEpisodeTorrentRepository::save)
                    .zipWith(delugeEpisodeTorrentRepository.findByIdAnimeEpisodeTorrent(animeEpisodeTorrent.id!!).singleOptional())
            }

            .map { it.t1.toAnimeEpisodeTorrentDTO(episodeNumber, it.t2.getOrNull()?.progress) }

    fun replaceEpisodeTorrent(
        malId: Long,
        episodeNumber: Int,
        animeEpisodeTorrent: AnimeEpisodeTorrentDTO
    ): Mono<AnimeEpisodeTorrentDTO> = animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
        .map { animeEpisodeTorrent.toModel(it.idAnimeEpisode).copy(id = it.id) }
        .flatMap(animeEpisodeTorrentRepository::save)
        .map { it.toAnimeEpisodeTorrentDTO(episodeNumber) }

    fun deleteEpisodeTorrent(malId: Long, episodeNumber: Int): Mono<Void> =
        animeEpisodeService.findAnimeEpisodeByAnimeIdAndEpisodeNumber(malId, episodeNumber)
            .flatMap { animeEpisodeTorrentRepository.deleteByMalIdAndIdAnimeEpisode(malId, it.id) }


    fun scanEpisodeTorrent(malId: Long): Flux<AnimeEpisodeTorrentDTO> = animeTorrentRepository.findById(malId)
        .flatMapMany { animeTorrent ->
            animeEpisodeTorrentRepository.findByMalId(malId)
                .map(AnimeEpisodeTorrent::idAnimeEpisode)
                .collectList().flatMapMany { listOfAnimeEpisodeTorrentIdAnimeEpisode ->
                    animeEpisodeService.findOrCreateAnimeEpisodesFromEpisode(malId, animeTorrent.lastEpisodeOnServer)
                        .filter { !listOfAnimeEpisodeTorrentIdAnimeEpisode.contains(it.id) }

                }
                .flatMap { animeEpisode ->
                    nyaaTorrentService.searchEpisodeTorrent(
                        malId,
                        animeEpisode.episodeNumber + animeTorrent.deltaEpisode,
                        animeTorrent.searchWords
                    ).next()
                        .map { it.toAnimeEpisodeTorrent(malId, animeEpisode.id) }
                        .flatMap(animeEpisodeTorrentRepository::save)
                        .map { it.toAnimeEpisodeTorrentDTO(animeEpisode.episodeNumber) }
                }

        }

    fun scanNextEpisode(malId: Long): Mono<AnimeEpisodeTorrentDTO> =
        animeTorrentRepository.findById(malId).zipWith(animeInServerRepository.findWithAnimeInformation(malId))
            .flatMap {
                if (it.t2.episodes == null || it.t1.lastEpisodeOnServer < it.t2.episodes!!) {
                    animeEpisodeService.findOrcreateAnimeEpisode(malId, it.t1.lastEpisodeOnServer + 1)
                } else Mono.empty()

            }
            .flatMap { scanTorrentByMalIdAndEpisodeNumber(malId, it.episodeNumber) }

    fun scanPackageTorrent(malId: Long): Mono<AnimeEpisodeTorrentDTO> {
        return scanTorrentByMalIdAndEpisodeNumber(malId, 0)
    }

    fun scanTorrentByMalIdAndEpisodeNumber(malId: Long, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO> {
        return animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber).singleOptional()
            .flatMapMany {
                if (it.isEmpty)
                    animeTorrentRepository.findById(malId)
                        .flatMapMany { nyaaTorrentService.searchEpisodeTorrent(malId, episodeNumber, it.searchWords) }
                else
                    Flux.empty()
            }
            .next()
            .zipWith(animeEpisodeService.findOrcreateAnimeEpisode(malId, episodeNumber))
            .map { it.t1.toAnimeEpisodeTorrent(malId, it.t2.id) }
            .flatMap(animeEpisodeTorrentRepository::save)
            .map { it.toAnimeEpisodeTorrentDTO(episodeNumber) }
    }


}