package fr.zakaoai.coldlibrarybackend.service


import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeEpisodeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import fr.zakaoai.coldlibrarybackend.enums.StorageState
import fr.zakaoai.coldlibrarybackend.infrastructure.JikanAPIService
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.TrackedAnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class AnimeService(
    private val repo: AnimeRepository,
    private val episodeRepo: AnimeEpisodeRepository,
    private val jikanService: JikanAPIService,
    private val trackedAnimeTorrentRepository: TrackedAnimeTorrentRepository
) {

    fun getAllAnime(): Flux<AnimeDTO> {
        return repo.findAll()
            .map(Anime::toAnimeDTO)
    }

    fun findAnimeAndSave(malId: Int): Mono<Anime> {
        return jikanService.getAnimeById(malId)
            .map(AnimeDTO::toModel)
            .flatMap { repo.save(it) };
    }

    fun updateAnimeAndSave(malId: Int): Mono<AnimeDTO> {
        return repo.findByMalId(malId)
            .flatMap { repoAnime ->
                jikanService.getAnimeById(malId)
                    .flatMap {
                        saveAnime(
                            it.copy(
                                storageState = repoAnime.storageState,
                                isComplete = repoAnime.isComplete,
                                lastAvaibleEpisode = repoAnime.lastAvaibleEpisode
                            ), repoAnime.id
                        )
                    }
            }
    }

    fun saveAnimeById(malId: Int): Mono<AnimeDTO> {
        return repo.findByMalId(malId)
            .switchIfEmpty(
                findAnimeAndSave(malId)
            ).map(Anime::toAnimeDTO)
    }

    fun deleteById(id: Int): Mono<Void> {
        return repo.deleteByMalId(id)
            .and(episodeRepo.deleteByMalId(id))
            .and(trackedAnimeTorrentRepository.deleteByMalId(id))
    }

    fun findByMalId(id: Int): Mono<AnimeDTO> {
        return repo.findByMalId(id)
            .map(Anime::toAnimeDTO)
    }

    fun searchAnime(search: String): Flux<AnimeDTO> {
        return jikanService.searchAnime(search)
            .flatMap { jikanAnime ->
                repo.findByMalId(jikanAnime.malId)
                    .map(Anime::toAnimeDTO)
                    .defaultIfEmpty(jikanAnime)
            }
    }

    fun updateAnime(animeDTO: AnimeDTO): Mono<AnimeDTO> {
        return repo.findByMalId(animeDTO.malId)
            .flatMap { saveAnime(animeDTO, it.id) }
    }

    fun saveAnime(
        anime: AnimeDTO,
        id: Long?
    ): Mono<AnimeDTO> {
        return Mono.just(anime)
            .map { anime.toModel(id) }
            .flatMap(repo::save)
            .map(Anime::toAnimeDTO)
    }

    fun updateAnimeStorageState(
        malId: Int,
        state: String
    ): Mono<AnimeDTO> {
        return repo.findByMalId(malId).flatMap { anime ->
            val dto = anime.toAnimeDTO()
            dto.storageState = StorageState.valueOf(state)
            updateAnime(dto)
        }
    }

    fun updateAnimeLastAvaibleEpisode(
        malId: Int,
        lastAvaibleEpisode: Int
    ): Mono<AnimeDTO> {
        return repo.findByMalId(malId).flatMap { anime ->
            anime.lastAvaibleEpisode = lastAvaibleEpisode
            repo.save(anime)
        }.map(Anime::toAnimeDTO)
    }

    fun updateAnimeIsComplete(
        malId: Int,
        isComplete: Boolean
    ): Mono<AnimeDTO> {
        return repo.findByMalId(malId).flatMap { anime ->
            val dto = anime.toAnimeDTO()
            dto.isComplete = isComplete
            if (isComplete) {
                dto.lastAvaibleEpisode = dto.nbEpisodes
            }
            updateAnime(dto)
        }
    }
}