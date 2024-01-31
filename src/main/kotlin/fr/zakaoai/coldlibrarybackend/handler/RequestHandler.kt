package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.model.dto.input.RequestInputDTO
import fr.zakaoai.coldlibrarybackend.service.RequestService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2

@Component
class RequestHandler(val requestService: RequestService) {

    fun createRequest(req: ServerRequest) =
        req.bodyToMono(RequestInputDTO::class.java)
            .flatMap(requestService::createRequest)
            .flatMap(ServerResponse.ok()::bodyValue)

    fun getMyRequest(req: ServerRequest) =
        requestService.getMyRequests()
            .collectList()
            .flatMap(ServerResponse.ok()::bodyValue)

    fun getMyAssignedRequest(req: ServerRequest) =
        requestService.getMyAssignedRequest()
            .collectList()
            .flatMap(ServerResponse.ok()::bodyValue)

    fun getAllRequest(req: ServerRequest) =
        requestService.getAllRequest()
            .collectList()
            .flatMap(ServerResponse.ok()::bodyValue)

    fun updateRequest(req: ServerRequest) =
        req.pathVariable("id").toLong().toMono().zipWith(req.bodyToMono(RequestInputDTO::class.java))
            .flatMap { (requestId, requestInputDTO) -> requestService.updateRequest(requestId, requestInputDTO) }
            .flatMap(ServerResponse.ok()::bodyValue)
}