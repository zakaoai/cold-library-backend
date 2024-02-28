package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.DelugeTorrentClient
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.DelugeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeEpisodeTorrentRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.DelugeEpisodeTorrentRepository
import fr.zakaoai.coldlibrarybackend.model.mapper.toDelugeEpisodeTorrent
import fr.zakaoai.coldlibrarybackend.model.mapper.toDelugeEpisodeTorrentDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import kotlin.io.path.Path
import kotlin.io.path.pathString

@Service
class DelugeTorrentService(
    val delugeTorrentClient: DelugeTorrentClient,
    val animeTorrentRepository: AnimeTorrentRepository,
    val delugeEpisodeTorrentRepository: DelugeEpisodeTorrentRepository,
    val animeEpisodeTorrentRepository: AnimeEpisodeTorrentRepository
) {

    private val logger = LoggerFactory.getLogger(DelugeTorrentService::class.java)

    fun downloadDeluge(animeEpisodeTorrent: AnimeEpisodeTorrent, animeTorrent: AnimeTorrent) =
        delugeTorrentClient.downloadTorrent(
            animeEpisodeTorrent.torrentLink,
            Path("/downloads", animeTorrent.torrentPath).pathString
        )
            .map { it.toDelugeEpisodeTorrent(animeEpisodeTorrent.id!!, animeEpisodeTorrent.torrentId) }

    fun downloadTorrent(malId: Long, episodeNumber: Int) =
        animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .zipWith(animeTorrentRepository.findById(malId))
            .flatMap { (animeEpisodeTorrent, animeTorrent) ->
                delugeEpisodeTorrentRepository.findByTorrentId(animeEpisodeTorrent.torrentId)
                    .switchIfEmpty(downloadDeluge(animeEpisodeTorrent, animeTorrent))
                    .map { it.copy(idAnimeEpisodeTorrent = animeEpisodeTorrent.id!!) }
                    .flatMap(delugeEpisodeTorrentRepository::save)
            }
            .map(DelugeEpisodeTorrent::toDelugeEpisodeTorrentDTO)

    fun updateTorrent(malId: Long, episodeNumber: Int) =
        animeEpisodeTorrentRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .flatMap { delugeEpisodeTorrentRepository.findByTorrentId(it.torrentId) }
            .flatMap { delugeEpisodeTorrent ->
                delugeTorrentClient.getDownloadTorrentStatus(delugeEpisodeTorrent.torrentHash)
                    .map { torrentStatus ->
                        torrentStatus.result.progress?.let { delugeEpisodeTorrent.copy(progress = it) }
                            ?: delugeEpisodeTorrent
                    }
            }
            .flatMap(delugeEpisodeTorrentRepository::save)
            .map(DelugeEpisodeTorrent::toDelugeEpisodeTorrentDTO)

    fun updateAllTorrent() = delugeEpisodeTorrentRepository.findAll()
        .filter { it.progress < 100 }
        .collectList().flatMapMany { torrentList ->
            delugeTorrentClient.getMultipleDownloadTorrentStatus(torrentList.map(DelugeEpisodeTorrent::torrentHash))
                .map { delugeTorrentStatus ->
                    torrentList.mapNotNull {
                        delugeTorrentStatus.result?.get(it.torrentHash)?.progress?.let { it1 ->
                            it.copy(
                                progress = it1
                            )
                        }
                    }
                }
                .flatMapMany(delugeEpisodeTorrentRepository::saveAll)
        }
        .map(DelugeEpisodeTorrent::toDelugeEpisodeTorrentDTO)


}