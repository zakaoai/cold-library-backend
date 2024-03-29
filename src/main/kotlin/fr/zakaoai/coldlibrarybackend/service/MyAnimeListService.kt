package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.extension.logger
import fr.zakaoai.coldlibrarybackend.infrastructure.MyAnimeListClient
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.User
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.UserRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.AnimeListStatus
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListInput
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListNode
import fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist.MALAnimeListResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MyAnimeListService(val myAnimeListClient: MyAnimeListClient, val userRepository: UserRepository) {

    fun getUserAnimeListByStatus(username: String, status: AnimeListStatus) = myAnimeListClient.getUserAnimeList(
        username,
        MALAnimeListInput(limit = 1000, status = status.value)
    ).expandDeep { listResp ->
        listResp.paging.next?.let(myAnimeListClient::expandUserAnimeList)
            ?.doOnSubscribe { logger().warn("Next Page %s".format(listResp.paging.next)) } ?: Mono.empty()
    }
        .map(MALAnimeListResponse::data)
        .map{ statusAnimeList -> statusAnimeList.map { it.node.copy(userStatus = status.value) }}
        .flatMapIterable { it }

    fun getUserAnimeList() = ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .flatMap(userRepository::findById)
        .mapNotNull(User::malUsername)
        .flatMapMany { username ->
            getUserAnimeListByStatus(username!!, AnimeListStatus.WATCHING)
                .mergeWith(getUserAnimeListByStatus(username, AnimeListStatus.COMPLETED))
                .mergeWith(getUserAnimeListByStatus(username, AnimeListStatus.DROPPED))
                .mergeWith(getUserAnimeListByStatus(username, AnimeListStatus.ON_HOLD))
                .mergeWith(getUserAnimeListByStatus(username, AnimeListStatus.PLAN_TO_WATCH))

        }



}