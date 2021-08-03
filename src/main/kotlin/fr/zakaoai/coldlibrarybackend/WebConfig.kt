package fr.zakaoai.coldlibrarybackend


import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Configuration
@EnableWebFlux
@ComponentScan("fr.zakaoai.coldlibrarybackend")
class WebConfig : WebFluxConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("OPTIONS, GET, POST, PUT, DELETE")
                .allowedHeaders("Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization, X-Requested-With, requestId, Correlation-Id")
    }
}


@RestControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(value = [NotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun resourceNotFoundException() {
        //
    }
}


@Bean
fun corsFilter(): CorsWebFilter {

    val config = CorsConfiguration()

    // Possibly...
    // config.applyPermitDefaultValues()

    config.allowCredentials = true
    config.addAllowedOrigin("*")
    config.addAllowedHeader("*")
    config.addAllowedMethod("*")

    val source = UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", config)
    }
    return CorsWebFilter(source)
}

@Component
class AddControlHeaderWebFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        exchange.response
                .headers
                .add("Access-Control-Allow-Headers", "*")

        exchange.response
                .headers
                .add("Access-Control-Allow-Origin", "*")

        exchange.response
                .headers
                .add("Access-Control-Allow-Methods", "*")

        return chain.filter(exchange)
    }
}

@Component
class RequestTimingFilter : WebFilter {

    val logger = LoggerFactory.getLogger(RequestTimingFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val startMillis = System.currentTimeMillis()
        return chain.filter(exchange)
                .doOnSuccess {
                    logger.info(
                            "Elapsed Time: {}ms",
                            System.currentTimeMillis() - startMillis
                    )
                }
    }
}


@Component
class LoggingFilter(val requestLogger: RequestLogger) : WebFilter {
    val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        logger.info(requestLogger.getRequestMessage(exchange))
        val filter = chain.filter(exchange)
        exchange.response.beforeCommit {
            logger.info(requestLogger.getResponseMessage(exchange))
            Mono.empty()
        }
        return filter
    }
}

@Component
class RequestLogger {

    fun getRequestMessage(exchange: ServerWebExchange): String {
        val request = exchange.request
        val method = request.method
        val path = request.uri.path
        val acceptableMediaTypes = request.headers.accept
        val contentType = request.headers.contentType
        return ">>> $method $path ${HttpHeaders.ACCEPT}: $acceptableMediaTypes ${HttpHeaders.CONTENT_TYPE}: $contentType"
    }

    fun getResponseMessage(exchange: ServerWebExchange): String {
        val request = exchange.request
        val response = exchange.response
        val method = request.method
        val path = request.uri.path
        val statusCode = getStatus(response)
        val contentType = response.headers.contentType
        return "<<< $method $path HTTP ${statusCode?.value()} ${statusCode?.reasonPhrase} ${HttpHeaders.CONTENT_TYPE}: $contentType"
    }

    private fun getStatus(response: ServerHttpResponse): HttpStatus? =
            try {
                response.statusCode
            } catch (ex: Exception) {
                HttpStatus.CONTINUE
            }
}