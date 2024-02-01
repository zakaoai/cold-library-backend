package fr.zakaoai.coldlibrarybackend.handler

import fr.zakaoai.coldlibrarybackend.enums.LogMessageHandler
import fr.zakaoai.coldlibrarybackend.service.CachingService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class CacheHandler(val cachingService: CachingService) : HandlerUtils() {

    fun clearAllCaches(req: ServerRequest): Mono<ServerResponse> =
        cachingService.evictAllCaches().toMono()
            .doOnNext {
                logRequest(
                    req,
                    LogMessageHandler.CACHE_CLEAR_ALL.message
                )
            }
            .then(ServerResponse.ok().build())
}
