package fr.zakaoai.coldlibrarybackend.service


import fr.zakaoai.coldlibrarybackend.enums.StorageState
import fr.zakaoai.coldlibrarybackend.infrastructure.JikanAPIService
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.Anime
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeInServer
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeInServerRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeRepository
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeDTO
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeInServer
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class AnimeService(
    private val animeInServerRepository: AnimeInServerRepository,
    private val animeRepository: AnimeRepository,
    private val jikanService: JikanAPIService,

    ) {

    fun getAllAnime(): Flux<AnimeDTO> = animeInServerRepository.findAllWithAnimeInformation()

    fun findAnimeInServerAndSave(malId: Long): Mono<AnimeDTO> = jikanService.getAnimeById(malId)
        .map(net.sandrohc.jikan.model.anime.Anime::toAnimeModel)
        .flatMap { animeRepository.findById(it.malId).switchIfEmpty(animeRepository.save(it.copy(isNew = true))) }
        .flatMap { animeDAO ->
            animeInServerRepository.findById(animeDAO.malId)
                .switchIfEmpty(animeInServerRepository.save(animeDAO.toAnimeInServer().copy(isNew = true)))
                .map { animeDAO.toAnimeDTO(it) }
        }

    fun updateAnimeAndSave(malId: Long): Mono<Anime> = animeRepository.findById(malId)
        .flatMap {
            jikanService.getAnimeById(malId)
                .flatMap {
                    animeRepository.save(it.toAnimeModel())
                }
        }

    fun deleteById(malId: Long): Mono<Void> = animeInServerRepository.deleteById(malId)

    fun findByMalId(id: Long): Mono<AnimeDTO> = animeInServerRepository.findWithAnimeInformation(id)

    fun searchAnime(search: String): Flux<AnimeDTO> {
        return jikanService.searchAnime(search)
            .flatMap { jikanAnime ->
                findByMalId(jikanAnime.malId.toLong())
                    .defaultIfEmpty(jikanAnime.toAnimeDTO())
            }
    }

    fun updateAnimeStorageState(
        malId: Long,
        state: String
    ): Mono<AnimeInServer> = animeInServerRepository.findById(malId)
        .map { it.copy(storageState = StorageState.valueOf(state)) }
        .flatMap(animeInServerRepository::save)

    fun updateAnimeLastAvaibleEpisode(
        malId: Long,
        lastAvaibleEpisode: Int
    ): Mono<AnimeInServer> = animeInServerRepository.findById(malId)
        .map { it.copy(lastAvaibleEpisode = lastAvaibleEpisode) }
        .flatMap(animeInServerRepository::save)


    fun updateAnimeIsComplete(
        malId: Long,
        isComplete: Boolean
    ): Mono<AnimeInServer> = animeInServerRepository.findById(malId)
        .map { it.copy(isComplete = isComplete) }
        .flatMap(animeInServerRepository::save)


}