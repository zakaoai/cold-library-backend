package fr.zakaoai.coldlibrarybackend.service


import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.projections.AnimeTorrentProjection
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeInServerRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeTorrentDTO
import org.springframework.stereotype.Service
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import java.time.DayOfWeek

@Service
class AnimeTorrentService(
    private val animeTorrentRepository: AnimeTorrentRepository,
    private val animeRepository: AnimeRepository,
    private val animeInServerRepository: AnimeInServerRepository
) {

    fun getAllTrackedAnime() = animeTorrentRepository.getAllDownloadingAnime()
        .map(AnimeTorrentProjection::toAnimeTorrentDTO)

    fun getTrackedAnime(malId: Long) = animeTorrentRepository.findById(malId)
        .zipWith(animeRepository.findById(malId))
        .map { (torrent, anime) -> torrent.toAnimeTorrentDTO(anime.title, anime.episodes) }

    fun updateTrackedAnime(animeTorrent: AnimeTorrent) = animeTorrentRepository.save(animeTorrent)
        .zipWith(animeRepository.findById(animeTorrent.malId))
        .map { (torrent, anime) -> torrent.toAnimeTorrentDTO(anime.title, anime.episodes) }

    fun createTrackedAnime(malId: Long) =
        animeInServerRepository.findById(malId)
            .map { it.copy(isDownloading = true) }
            .flatMap(animeInServerRepository::save)
            .then(animeRepository.findById(malId))
            .map { AnimeTorrent(malId, 0, it.title, DayOfWeek.MONDAY, 0, "/${it.title}", true) }
            .flatMap { animeTorrentRepository.findById(malId).switchIfEmpty(animeTorrentRepository.save(it)) }
            .zipWith(animeRepository.findById(malId))
            .map { (torrent, anime) -> torrent.toAnimeTorrentDTO(anime.title, anime.episodes) }

    fun deleteTrackedAnime(malId: Long) = animeInServerRepository.findById(malId)
        .map { it.copy(isDownloading = false) }
        .flatMap(animeInServerRepository::save)
        .then(animeTorrentRepository.deleteById(malId))

    fun updateLastEpisodeOnServer(malId: Long, lastEpisodeOnServer: Int) = animeTorrentRepository.findById(malId)
        .map { it.copy(lastEpisodeOnServer = lastEpisodeOnServer) }
        .flatMap(animeTorrentRepository::save)
        .zipWith(animeRepository.findById(malId))
        .map { (torrent, anime) -> torrent.toAnimeTorrentDTO(anime.title, anime.episodes) }

}