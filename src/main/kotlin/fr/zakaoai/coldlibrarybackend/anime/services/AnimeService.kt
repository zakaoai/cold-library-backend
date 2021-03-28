package fr.zakaoai.coldlibrarybackend.anime.services


import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.toModel
import fr.zakaoai.coldlibrarybackend.anime.api.JikanAPIService
import fr.zakaoai.coldlibrarybackend.anime.enums.StorageState
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeEpisodeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class AnimeService(private val repo: AnimeRepository,private val episodeRepo: AnimeEpisodeRepository, private val jikanService: JikanAPIService) {

    fun getAllAnime(): Flux<AnimeDTO> {
        return repo.findAll().map(Anime::toAnimeDTO)
    }

    fun saveAnimeById(malId: Int): Mono<AnimeDTO> {

        return repo.findByMalId(malId)
            .switchIfEmpty(jikanService.getAnimeById(malId).map(AnimeDTO::toModel).flatMap { repo.save(it) }
            ).map(Anime::toAnimeDTO)

    }

    fun deleteById(id: Int): Mono<Void> {
        return repo.deleteByMalId(id).and(episodeRepo.deleteByMalId(id))
    }

    fun findByMalId(id: Int): Mono<AnimeDTO> {
        return repo.findByMalId(id).map(Anime::toAnimeDTO)
    }

    fun searchAnime(search: String): Flux<AnimeDTO> {
        return jikanService.searchAnime(search)
            .flatMap { jikanAnime ->
                repo.findByMalId(jikanAnime.malId).map(Anime::toAnimeDTO).defaultIfEmpty(jikanAnime)
            }
    }

    fun updateAnime(animeDTO: AnimeDTO): Mono<AnimeDTO> {
        return repo.findByMalId(animeDTO.malId)
            .map { anime -> animeDTO.toModel(anime.id) }
            .flatMap(repo::save)
            .map(Anime::toAnimeDTO)
    }

    fun updateAnimeStorageState(malId: Int, state: String): Mono<AnimeDTO> {
        return repo.findByMalId(malId).flatMap { anime ->
            val dto = anime.toAnimeDTO()
            dto.storageState = StorageState.valueOf(state)
            updateAnime(dto)
        }
    }

    fun updateAnimeLastAvaibleEpisode(malId: Int, lastAvaibleEpisode: Int): Mono<AnimeDTO> {
        return repo.findByMalId(malId).flatMap { anime ->
            anime.lastAvaibleEpisode = lastAvaibleEpisode
            repo.save(anime)
        }.map(Anime::toAnimeDTO)
    }

    fun updateAnimeIsComplete(malId: Int, isComplete: Boolean): Mono<AnimeDTO> {
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