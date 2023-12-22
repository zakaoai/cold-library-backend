package fr.zakaoai.coldlibrarybackend.service


import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.TrackedAnimeTorrent
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeEpisodeTorrentRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.TrackedAnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.model.dto.response.TrackedAnimeTorrentDTO
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.DayOfWeek

@Service
class TrackedAnimeTorrentService(
    private val trackedAnimeTorrentRepository: TrackedAnimeTorrentRepository,
    private val animeEpisodeTorrentRepository: AnimeEpisodeTorrentRepository,
    private val animeRepository: AnimeRepository
) {

    fun getAllTrackedAnime(): Flux<TrackedAnimeTorrentDTO> {
        return trackedAnimeTorrentRepository.findAll()
            .map(TrackedAnimeTorrent::toTrackedAnimeTorrentDTO)
    }

    fun getTrackedAnime(malId: Int): Mono<TrackedAnimeTorrentDTO> {
        return trackedAnimeTorrentRepository.findByMalId(malId)
            .map(TrackedAnimeTorrent::toTrackedAnimeTorrentDTO)
    }

    fun updateTrackedAnime(trackedAnimeTorrentDTO: TrackedAnimeTorrentDTO): Mono<TrackedAnimeTorrentDTO> {
        return trackedAnimeTorrentRepository.findByMalId(trackedAnimeTorrentDTO.malId)
            .map {
                TrackedAnimeTorrent(
                    it.id,
                    it.malId,
                    trackedAnimeTorrentDTO.lastEpisodeOnServer,
                    trackedAnimeTorrentDTO.searchWords,
                    trackedAnimeTorrentDTO.dayOfRelease
                )
            }
            .flatMap(trackedAnimeTorrentRepository::save)
            .map(TrackedAnimeTorrent::toTrackedAnimeTorrentDTO)
    }

    fun createTrackedAnime(malId: Int): Mono<TrackedAnimeTorrentDTO> {
        return animeRepository.findByMalId(malId)
            .map { TrackedAnimeTorrent(null, malId, it.nbEpisodes, it.title, DayOfWeek.MONDAY) }
            .flatMap(trackedAnimeTorrentRepository::save)
            .map(TrackedAnimeTorrent::toTrackedAnimeTorrentDTO)
    }

    fun deleteTrackedAnime(malId: Int): Mono<Void> {
        return trackedAnimeTorrentRepository.deleteByMalId(malId)
            .and(animeEpisodeTorrentRepository.deleteByMalId(malId))
    }
}