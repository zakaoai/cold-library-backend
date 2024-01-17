package fr.zakaoai.coldlibrarybackend.service

//import fr.zakaoai.coldlibrarybackend.infrastructure.NyaaTorrentService
//import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
//import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeEpisodeTorrentRepository
//import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeInServerRepository
//import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeTorrentRepository
//import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeDTO
//import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeEpisodeTorrentDTO
//import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeEpisodeTorrent
//import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeEpisodeTorrentDTO
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Service
//import reactor.core.publisher.Flux
//import reactor.core.publisher.Mono
//import reactor.kotlin.core.publisher.toFlux
//
//@Service
//class AnimeEpisodeTorrentService(
//    private val animeEpisodeTorrentRepository: AnimeEpisodeTorrentRepository,
//    private val animeTorrentRepository: AnimeTorrentRepository,
//    private val animeInServerRepository: AnimeInServerRepository,
//    private val animeEpisodeService: AnimeEpisodeService,
//    private val nyaaTorrentService: NyaaTorrentService
//) {
//
//    private val logger = LoggerFactory.getLogger(AnimeEpisodeTorrentService::class.java)
//
//
//    fun findAnimeEpisodeTorrentByMalId(malId: Long) = animeEpisodeTorrentRepository.findByMalId(malId)
//
//    fun searchAlternateEpisodeTorrent(malId: Long, episodeNumber: Int): Flux<AnimeEpisodeTorrentDTO> =
//        animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
//            .flatMapMany { animeEpisodeTorrent ->
//                animeTorrentRepository.findById(malId)
//                    .flatMapMany {
//                        nyaaTorrentService.searchEpisodeTorrent(
//                            malId,
//                            episodeNumber,
//                            it.searchWords
//                        )
//                    }
//                    .filter { torrentPreview -> torrentPreview.id != animeEpisodeTorrent.torrentId }
//                    .map { it.toAnimeEpisodeTorrent(malId, animeEpisodeTorrent.idAnimeEpisode) }
//            }
//            .map { it.toAnimeEpisodeTorrentDTO(episodeNumber) }
//
//    fun updateEpisodeTorrent(malId: Long, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO> =
//        animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
//            .flatMap { animeEpisodeTorrent ->
//                nyaaTorrentService.searchEpisodeTorrentById(animeEpisodeTorrent.torrentId)
//                    .map {
//                        it.toAnimeEpisodeTorrent(
//                            malId,
//                            animeEpisodeTorrent.idAnimeEpisode,
//                            animeEpisodeTorrent.torrentId
//                        )
//                    }
//                    .flatMap(animeEpisodeTorrentRepository::save)
//            }
//            .map { it.toAnimeEpisodeTorrentDTO(episodeNumber) }
//
//    fun replaceEpisodeTorrent(
//        malId: Long,
//        episodeNumber: Int,
//        animeEpisodeTorrent: AnimeEpisodeTorrentDTO
//    ): Mono<AnimeEpisodeTorrentDTO> = animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
//        .flatMap { saveAnimeTorrentWithId(animeEpisodeTorrent, it.id) }
//
//    fun deleteEpisodeTorrent(malId: Long, episodeNumber: Int): Mono<Void> =
//        animeEpisodeService.findAnimeEpisodeByAnimeIdAndEpisodeNumber(malId, episodeNumber)
//            .flatMap { animeEpisodeTorrentRepository.deleteByMalIdAndIdAnimeEpisode(malId, it.id) }
//
//    fun isSameEpisodeNumber(animeEpisode: AnimeEpisodeDTO, episodeNumber: Int): Boolean {
//        return animeEpisode.episodeNumber == episodeNumber
//    }
//
//    fun isAnimeEpisodeInEpisodeNumbers(animeEpisode: AnimeEpisodeDTO, episodeNumbers: List<Int>): Boolean {
//        return episodeNumbers.any { episodeNumber -> isSameEpisodeNumber(animeEpisode, episodeNumber) }
//    }
//
//    fun filterAnimeEpisodeList(malId: Long, animeEpisodeList: List<AnimeEpisodeDTO>): Flux<AnimeEpisodeDTO> {
//        return animeEpisodeTorrentRepository.findByMalId(malId)
//            .map(AnimeEpisodeTorrent::episodeNumber)
//            .collectList()
//            .flatMapMany { episodeNumbers ->
//                animeEpisodeList.filter { animeEpisode ->
//                    !isAnimeEpisodeInEpisodeNumbers(
//                        animeEpisode,
//                        episodeNumbers
//                    )
//                }.toFlux()
//            }
//    }
//
//
//    fun scanEpisodeTorrent(malId: Long): Flux<AnimeEpisodeTorrentDTO> {
//        return trackedAnimeTorrentRepository.findByMalId(malId)
//            .map(TrackedAnimeTorrent::lastEpisodeOnServer)
//            .flatMapMany { lastAvaible ->
//                episodeService.searchEpisodesByAnimeId(malId)
//                    .filter { ep -> ep.episodeNumber > lastAvaible }
//            }
//            .collectList()
//            .flatMapMany { animeList -> filterAnimeEpisodeList(malId, animeList) }
//            .flatMap { nyaaTorrentService.searchEpisodeTorrent(malId, it.episodeNumber).next() }
//            .flatMap(this::saveAnimeTorrent)
//    }
//
//    fun scanNextEpisode(malId: Long): Mono<AnimeEpisodeTorrentDTO> =
//        animeTorrentRepository.findById(malId).zipWith(animeInServerRepository.findWithAnimeInformation(malId))
//            .flatMap {
//                if (it.t2.episodes == null || it.t1.lastEpisodeOnServer < it.t2.episodes!!) {
//                    animeEpisodeService.findOrcreateAnimeEpisode(malId, it.t1.lastEpisodeOnServer + 1)
//                } else Mono.empty()
//
//            }
//            .flatMap { scanTorrentByMalIdAndEpisodeNumber(malId, it.episodeNumber) }
//
//    fun scanPackageTorrent(malId: Long): Mono<AnimeEpisodeTorrentDTO> {
//        return scanTorrentByMalIdAndEpisodeNumber(malId, 0)
//    }
//
//    fun scanTorrentByMalIdAndEpisodeNumber(malId: Long, episodeNumber: Int): Mono<AnimeEpisodeTorrentDTO> {
//        return animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber).singleOptional()
//            .flatMap {
//                if (it.isEmpty)
//                    nyaaTorrentService.searchEpisodeTorrent(malId, episodeNumber).next()
//                        .flatMap(this::saveAnimeTorrent)
//                else
//                    Mono.empty()
//            }
//    }
//
//    fun saveAnimeTorrentWithId(torrent: AnimeEpisodeTorrentDTO, id: Long?): Mono<AnimeEpisodeTorrentDTO> {
//        return Mono.just(torrent)
//            .map { it.toModel(id) }
//            .flatMap(animeEpisodeTorrentRepository::save)
//            .map(AnimeEpisodeTorrent::toAnimeEpisodeTorrentDTO)
//    }
//
//    fun saveAnimeTorrent(torrent: AnimeEpisodeTorrentDTO): Mono<AnimeEpisodeTorrentDTO> {
//        return saveAnimeTorrentWithId(torrent, null)
//    }
//
//}