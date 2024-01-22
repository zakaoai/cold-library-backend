package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.MyAnimeListClient
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListInput
import org.springframework.stereotype.Service

@Service
class MyAnimeListService(val myAnimeListClient: MyAnimeListClient) {

    fun getUserAnimeList() = myAnimeListClient.getUserAnimeList("zakaoai", MALAnimeListInput())
}