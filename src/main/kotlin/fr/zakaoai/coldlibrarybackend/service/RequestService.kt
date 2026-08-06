package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.RequestRepository
import fr.zakaoai.coldlibrarybackend.model.dto.input.RequestInputDTO
import fr.zakaoai.coldlibrarybackend.model.dto.response.RequestDTO
import fr.zakaoai.coldlibrarybackend.model.mapper.toRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2

@Service
class RequestService(
    val requestRepository: RequestRepository,
    val animeService: AnimeService
) {

    fun createRequest(request: RequestInputDTO): Mono<RequestDTO> = animeService.findAnimeAndSave(request.malId)
        .then(ReactiveSecurityContextHolder.getContext())
        .mapNotNull(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .map(request::toRequest)
        .flatMap(requestRepository::save)
        .flatMap { requestRepository.findByIdWithInformation(it.id!!) }

    fun getMyRequests(): Flux<RequestDTO> = ReactiveSecurityContextHolder.getContext()
        .mapNotNull(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .flatMapMany(requestRepository::findByCreatorId)

    fun getMyAssignedRequest(): Flux<RequestDTO> = ReactiveSecurityContextHolder.getContext()
        .mapNotNull(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .flatMapMany(requestRepository::findByAssignedUserId)

    fun getAllRequest(): Flux<RequestDTO> = requestRepository.findAllWithInformation()

    fun updateRequest(requestId: Long, requestInputDTO: RequestInputDTO): Mono<RequestDTO> = requestRepository.findById(requestId).map {
        it.copy(
            type = requestInputDTO.type,
            assignedUserId = requestInputDTO.assignedUserId,
            state = requestInputDTO.state
        )
    }
        .flatMap(requestRepository::save)
        .then(requestRepository.findByIdWithInformation(requestId))

    fun deleteRequest(requestId: Long): Mono<Void> = ReactiveSecurityContextHolder.getContext()
        .mapNotNull(SecurityContext::getAuthentication)
        .map { Pair(it.name,it.authorities) }
        .flatMap { p -> requestRepository.findById(requestId)
            .filter { request -> request.userId == p.first || p.second.any { it.authority == "admin" }}
        }
        .switchIfEmpty(Mono.error(IllegalAccessException("Vous n'êtes pas autorisé à supprimer cette demande")))
        .flatMap { requestRepository.deleteById(it.id!!) }

}