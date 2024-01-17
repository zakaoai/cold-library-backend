package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.JikanAPIService
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeEpisode
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.AnimeInServer
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeEpisodeRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeInServerRepository
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeEpisode
import fr.zakaoai.coldlibrarybackend.model.mapper.toAnimeEpisodeDTO
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AnimeEpisodeService(
    private val animeEpisodeRepository: AnimeEpisodeRepository,
    private val animeInServerRepository: AnimeInServerRepository,
    private val jikanAPIService: JikanAPIService
) {

    fun findAnimeEpisodeByAnimeId(malId: Long) = animeEpisodeRepository.findByMalId(malId)
        .map(AnimeEpisode::toAnimeEpisodeDTO)

    fun findAnimeEpisodeByAnimeIdAndEpisodeNumber(
        malId: Long,
        episodeNumber: Int
    ) = animeEpisodeRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
        .map(AnimeEpisode::toAnimeEpisodeDTO)

    fun findOrcreateAnimeEpisode(malId: Long, episodeNumber: Int) =
        animeEpisodeRepository.findByMalIdAndEpisodeNumber(malId, episodeNumber)
            .switchIfEmpty(jikanAPIService.getAnimeEpisodeByAnimeIdAndEpisodeNumber(malId, episodeNumber)
                .map { it.toAnimeEpisode(malId) }
                .defaultIfEmpty(AnimeEpisode(null, malId, episodeNumber, "Episode $episodeNumber", null, null))
                .flatMap(animeEpisodeRepository::save))

    fun createAnimeEpisodeFromListOfEpisodeNumber(malId: Long, episodeNumbers: List<Int>) =
        jikanAPIService.getAnimeEpisodesFromEpisodeByAnimeIdAndEpisodeNumber(malId, episodeNumbers.first())
            .filter { episodeNumbers.contains(it.malId) }
            .map { it.toAnimeEpisode(malId) }
            .collectList()
            .flatMapMany(animeEpisodeRepository::saveAll)

    fun findOrCreateAnimeEpisodesFromEpisode(malId: Long, episodeNumber: Int) =
        animeEpisodeRepository.findByMalId(malId)
            .filter { it.episodeNumber >= episodeNumber }
            .collectList()
            .zipWith(animeInServerRepository.findById(malId).map(AnimeInServer::lastAvaibleEpisode))
            .flatMap {
                val listOfEpisodeNumber =
                    (episodeNumber..it.t2).filter { number -> it.t1.none { animeEpisode -> animeEpisode.episodeNumber == number } }
                if (listOfEpisodeNumber.isNotEmpty()) {
                    createAnimeEpisodeFromListOfEpisodeNumber(malId, listOfEpisodeNumber)
                        .collectList()
                        .map { newEpisodes ->
                            newEpisodes.addAll(it.t1)
                            newEpisodes
                        }
                        .map { episode ->
                            episode.sortBy(AnimeEpisode::episodeNumber)
                            episode
                        }
                } else
                    Mono.just(it.t1)
            }.flatMapMany { Flux.fromIterable(it) }


    fun deleteEpisodeByMalId(malId: Long) = animeEpisodeRepository.deleteByMalId(malId)


}