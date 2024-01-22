package fr.zakaoai.coldlibrarybackend.infrastructure

import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListInput
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListResponse
import reactor.core.publisher.Mono

interface MyAnimeListClient {

    fun getUserAnimeList(myAnimeListUserName: String, malAnimeListInput: MALAnimeListInput): Mono<MALAnimeListResponse>
}