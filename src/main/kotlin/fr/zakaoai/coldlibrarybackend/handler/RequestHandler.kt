package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.model.dto.input.RequestInputDTO
import fr.zakaoai.coldlibrarybackend.service.RequestService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class RequestHandler(val requestService: RequestService) : HandlerUtils() {

    fun createRequest(req: ServerRequest) =
        req.bodyToMono(RequestInputDTO::class.java)
            .flatMap(requestService::createRequest)
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.REQUEST_CREATE.message.format(it.malId, it.type)
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun getMyRequest(req: ServerRequest) =
        requestService.getMyRequests()
            .collectList()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.REQUEST_GET_MY.message
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun getMyAssignedRequest(req: ServerRequest) =
        requestService.getMyAssignedRequest()
            .collectList()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.REQUEST_GET_MY_ASSIGNED.message
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun getAllRequest(req: ServerRequest) =
        requestService.getAllRequest()
            .collectList()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.REQUEST_GET_ALL.message
                )
            }
            .flatMap(ServerResponse.ok()::bodyValue)

    fun updateRequest(req: ServerRequest) = req.bodyToMono(RequestInputDTO::class.java)
        .flatMap { requestService.updateRequest(req.pathVariable("requestId").toLong(), it) }
        .doOnNext {
            logRequest(
                req,
                LogMessageHandler.REQUEST_UPDATE.message.format(
                    req.pathVariable("requestId").toLong()
                )
            )
        }
        .flatMap(ServerResponse.ok()::bodyValue)
}