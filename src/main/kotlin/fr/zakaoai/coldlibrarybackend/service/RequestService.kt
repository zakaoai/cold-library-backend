package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.RequestRepository
import fr.zakaoai.coldlibrarybackend.model.dto.input.RequestInputDTO
import fr.zakaoai.coldlibrarybackend.model.mapper.toRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service

@Service
class RequestService(
    val requestRepository: RequestRepository,
    val animeService: AnimeService
) {

    fun createRequest(request: RequestInputDTO) = animeService.findAnimeAndSave(request.malId)
        .then(ReactiveSecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .map(request::toRequest)
        .flatMap(requestRepository::save)
        .flatMap { requestRepository.findByIdWithInformation(it.id!!) }

    fun getMyRequests() = ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .flatMapMany(requestRepository::findByCreatorId)

    fun getMyAssignedRequest() = ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .flatMapMany(requestRepository::findByAssignedUserId)

    fun getAllRequest() = requestRepository.findAllWithInformation()

    fun updateRequest(requestId: Long, requestInputDTO: RequestInputDTO) = requestRepository.findById(requestId).map {
        it.copy(type = requestInputDTO.type, assignedUserId = requestInputDTO.assignedUserId, state = requestInputDTO.state)
    }
        .flatMap(requestRepository::save)
        .then(requestRepository.findByIdWithInformation(requestId))

}