package fr.zakaoai.coldlibrarybackend.service


import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeInServerRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeTorrentDTO
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
class AnimeTorrentService(
    private val animeTorrentRepository: AnimeTorrentRepository,
    private val animeRepository: AnimeRepository,
    private val animeInServerRepository: AnimeInServerRepository
) {

    fun getAllTrackedAnime() = animeTorrentRepository.getAllDownloadingAnime()
        .map(AnimeTorrent::toAnimeTorrentDTO)

    fun getTrackedAnime(malId: Long) = animeTorrentRepository.findById(malId)
        .map(AnimeTorrent::toAnimeTorrentDTO)

    fun updateTrackedAnime(animeTorrent: AnimeTorrent) = animeTorrentRepository.save(animeTorrent)
        .map(AnimeTorrent::toAnimeTorrentDTO)

    fun createTrackedAnime(malId: Long) = animeRepository.findById(malId)
        .map { AnimeTorrent(malId, 0, it.title, DayOfWeek.MONDAY, 0, "/${it.title}", true) }
        .flatMap{ animeTorrentRepository.findById(malId).switchIfEmpty(animeTorrentRepository.save(it))}
        .map(AnimeTorrent::toAnimeTorrentDTO)

    fun deleteTrackedAnime(malId: Long) = animeInServerRepository.findById(malId)
        .map { it.copy(isDownloading = false) }
        .flatMap(animeInServerRepository::save)
        .then(animeTorrentRepository.deleteById(malId))

}