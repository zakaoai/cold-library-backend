package fr.zakaoai.coldlibrarybackend.config.webclient

import fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge.DelugeJsonRPCInput
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.http.client.ReactorResourceFactory
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.*
import reactor.kotlin.core.publisher.toMono
import reactor.netty.http.client.HttpClient
import java.util.function.Consumer
import kotlin.random.Random


@Configuration
class DelugeWebClient {

    private val LOG = LoggerFactory.getLogger(DelugeWebClient::class.java)
    val delugeUrl = "http://192.168.1.15:8113"
    val delugePassword = "deluge"
    val delugeLoginBody = DelugeJsonRPCInput(
        "auth.login",
        listOf(delugePassword),
        Random.nextInt(0, Int.MAX_VALUE)
    )

    @Bean("webClient")
    fun webClient(
        resourceFactory: ReactorResourceFactory,
        authFilter: ExchangeFilterFunction
    ): WebClient {
        val httpClient = HttpClient.create(resourceFactory.connectionProvider)
        val clientHttpConnector = ReactorClientHttpConnector(httpClient)
        return WebClient.builder().baseUrl(delugeUrl).filter(authFilter)
            .clientConnector(clientHttpConnector)
            .build()
    }

    @Bean("authWebClient")
    fun authWebClient(resourceFactory: ReactorResourceFactory): WebClient {
        val httpClient = HttpClient.create(resourceFactory.connectionProvider)
        val clientHttpConnector = ReactorClientHttpConnector(httpClient)
        return WebClient.builder().baseUrl(delugeUrl).clientConnector(clientHttpConnector)
            .filters {
                it.add(logRequest())
                it.add(logResponse())
            }.build()
    }


    @Bean
    fun authFilter(
        @Qualifier("authWebClient") authWebClient: WebClient
    ) = ExchangeFilterFunction { request: ClientRequest, next: ExchangeFunction ->
        authWebClient.post()
            .uri("/json")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                delugeLoginBody
            )
            .exchangeToMono { response: ClientResponse ->
                next.exchange(
                    ClientRequest.from(
                        request
                    )
                        .cookies(readCookies(response))
                        .build()
                )
            }
    }

    private fun readCookies(response: ClientResponse) = { cookies: MultiValueMap<String, String> ->
        response.cookies().forEach { (responseCookieName: String, responseCookies: List<ResponseCookie>) ->
            cookies.addAll(
                responseCookieName,
                responseCookies.map(ResponseCookie::getValue)
                    .toList()
            )
        }
    }

    fun logRequest() = ExchangeFilterFunction.ofRequestProcessor { request: ClientRequest ->
        logMethodAndUrl(request)
        logHeaders(request)
        request.toMono()
    }


    fun logResponse() = ExchangeFilterFunction.ofResponseProcessor { response: ClientResponse ->
        logStatus(response)
        logHeaders(response)
        //logBody(response)
        response.toMono()
    }


    private fun logStatus(response: ClientResponse) {
        val status: HttpStatusCode = response.statusCode()
        LOG.info("Returned status code {}", status.value())
    }


    private fun logBody(response: ClientResponse) = response.bodyToMono(String::class.java)
        .doOnNext { LOG.info("Body is {}", it) }
        .then(response.toMono())


    private fun logHeaders(response: ClientResponse) =
        response.headers().asHttpHeaders().forEach { name: String, values: List<String> ->
            values.forEach(
                Consumer { value: String ->
                    logNameAndValuePair(name, value)
                })
        }


    private fun logHeaders(request: ClientRequest) = request.headers().forEach { name: String, values: List<String> ->
        values.forEach(
            Consumer { value: String ->
                logNameAndValuePair(name, value)
            })
    }


    private fun logNameAndValuePair(name: String, value: String) {
        LOG.info("{}={}", name, value)
    }


    private fun logMethodAndUrl(request: ClientRequest) {
        val sb = StringBuilder()
        sb.append(request.method().name())
        sb.append(" to ")
        sb.append(request.url())

        LOG.info(sb.toString())
    }
}