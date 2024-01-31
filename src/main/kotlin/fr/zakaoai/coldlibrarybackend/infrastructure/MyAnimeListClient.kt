package fr.zakaoai.coldlibrarybackend.infrastructure

import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListInput
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListResponse
import net.sandrohc.jikan.model.season.Season
import reactor.core.publisher.Mono

interface MyAnimeListClient {

    fun getUserAnimeList(myAnimeListUserName: String, malAnimeListInput: MALAnimeListInput): Mono<MALAnimeListResponse>

    fun getAnimeSeason(year: Int,season: Season): Mono<MALAnimeListResponse>
}