package fr.zakaoai.coldlibrarybackend.handler


import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
data class GlobalErrorHandler(val mapper: ObjectMapper) : ServerAccessDeniedHandler {
//    override fun handle(exchange: ServerWebExchange, denied: AccessDeniedException?): Mono<Void> {
//        val response = exchange.response
//        val bufferFactory = response.bufferFactory()
//        if (error is ResponseStatusException) {
//            val status: HttpStatusCode = error.statusCode
//            response.setStatusCode(status)
//            if (status == HttpStatus.NOT_FOUND) {
//                val body = Mono.just("Not Found".toByteArray())
//                    .map { bytes: ByteArray? ->
//                        bufferFactory.wrap(
//                            bytes!!
//                        )
//                    }
//                return response.writeWith(body)
//            }
//        }
//        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
//        return response.writeWith(
//            Mono.just(error.message?.toByteArray()?: "".toByteArray())
//                .map { bytes: ByteArray? ->
//                    bufferFactory.wrap(
//                        bytes!!
//                    )
//                })
//    }

    fun handleAuthenticationError(exchange: ServerWebExchange, error: AuthenticationException): Mono<Void> {
        val response = exchange.response
        val message = "Unauthorized. %s".formatted(error.message)
        val body = Mono.just(message.toByteArray())
            .map { bytes: ByteArray? ->
                response.bufferFactory().wrap(
                    bytes!!
                )
            }
        response.setStatusCode(HttpStatus.UNAUTHORIZED)
        return response.writeWith(body)
    }

    fun handleAccessDenied(
        exchange: ServerWebExchange,
        error: AccessDeniedException? // NOSONAR
    ): Mono<Void> {
        val response = exchange.response
        val body = Mono.just("Permission denied".toByteArray())
            .map { bytes: ByteArray? ->
                response.bufferFactory().wrap(
                    bytes!!
                )
            }
        response.setStatusCode(HttpStatus.FORBIDDEN)
        return response.writeWith(body)
    }

    override fun handle(exchange: ServerWebExchange?, denied: AccessDeniedException?): Mono<Void> {
        val response = exchange?.response
        val body = Mono.just("Permission denied".toByteArray())
            .map { bytes: ByteArray? ->
                response?.bufferFactory()?.wrap(
                    bytes!!
                )
            }
        response?.setStatusCode(HttpStatus.FORBIDDEN)
        return response?.writeWith(body) ?: Mono.empty()
    }


}