package fr.zakaoai.coldlibrarybackend.infrastructure.implementation

import fr.zakaoai.coldlibrarybackend.extension.queryParamNotNull
import fr.zakaoai.coldlibrarybackend.infrastructure.MyAnimeListClient
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListInput
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListResponse
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
                    .build(myAnimeListUserName)
            }


            .retrieve()
            .bodyToMono(MALAnimeListResponse::class.java)
}


