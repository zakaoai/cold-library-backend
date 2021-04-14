package fr.zakaoai.coldlibrarybackend.torrent.services

import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeRepository
import fr.zakaoai.coldlibrarybackend.torrent.DTO.TrackedAnimeTorrentDTO
import fr.zakaoai.coldlibrarybackend.torrent.repository.AnimeEpisodeTorrentRepository
import fr.zakaoai.coldlibrarybackend.torrent.repository.TrackedAnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.torrent.repository.entity.TrackedAnimeTorrent
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

    fun createTrackedAnime(malId: Int): Mono<TrackedAnimeTorrentDTO> {
        return animeRepository.findByMalId(malId)
            .map { TrackedAnimeTorrent(null, malId, it.title, DayOfWeek.MONDAY) }
            .flatMap(trackedAnimeTorrentRepository::save)
            .map(TrackedAnimeTorrent::toTrackedAnimeTorrentDTO)
    }

    fun deleteTrackedAnime(malId: Int): Mono<Void> {
        return animeRepository.deleteByMalId(malId).and(animeEpisodeTorrentRepository.deleteByMalId(malId))
    }
}