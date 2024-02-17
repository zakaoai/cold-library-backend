package fr.zakaoai.coldlibrarybackend.infrastructure.implementation

import fr.zakaoai.coldlibrarybackend.extension.queryParamNotNull
import fr.zakaoai.coldlibrarybackend.infrastructure.MyAnimeListClient
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListInput
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListResponse
import net.sandrohc.jikan.model.season.Season
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.CacheConfig
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
@CacheConfig
class MyAnimeListClientImpl(@Qualifier("MALWebClient") private val webClient: WebClient) : MyAnimeListClient {
    override fun getUserAnimeList(
        myAnimeListUserName: String,
        malAnimeListInput: MALAnimeListInput
    ): Mono<MALAnimeListResponse> =
        webClient.get()
            .uri {
                it.path("/v2/users/{username}/animelist")
                    .queryParamNotNull("status", malAnimeListInput.status)
                    .queryParamNotNull("sort", malAnimeListInput.sort)
                    .queryParam("limit", malAnimeListInput.limit)
                    .queryParam("offset", malAnimeListInput.offset)
                    .queryParam(
                        "fields",
                        "start_date,end_date,mean,rank,popularity,nsfw,genres,media_type,status,num_episodes,start_season,broadcast,rating"
                    )
                    .queryParam("nsfw", "true")
                    .build(myAnimeListUserName)
            }


            .retrieve()
            .bodyToMono(MALAnimeListResponse::class.java)

    override fun getAnimeSeason(year: Int, season: Season): Mono<MALAnimeListResponse> =
        webClient.get()
            .uri {
                it.path("/v2/anime/season/{year}/{season}")
                    .queryParam("limit", "1000")
                    .queryParam(
                        "fields",
                        "start_date,end_date,mean,rank,popularity,nsfw,genres,media_type,status,num_episodes,start_season,broadcast,rating"
                    )
                    .queryParam("nsfw", "true")
                    .build(year, season)
            }
            .retrieve()
            .bodyToMono(MALAnimeListResponse::class.java)
}


