package fr.zakaoai.coldlibrarybackend.config.client

import fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge.DelugeJsonRPCInput
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.http.client.ReactorResourceFactory
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.*
import reactor.netty.http.client.HttpClient
import kotlin.random.Random


@Configuration
class DelugeWebClient {

    @Value("\${deluge.host}")
    lateinit var delugeUrl: String

    @Value("\${deluge.password}")
    lateinit var delugePassword: String

    @Bean
    fun getLoginBody() = DelugeJsonRPCInput(
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
            .build()
    }

    @Bean
    fun authFilter(
        @Qualifier("authWebClient") authWebClient: WebClient,
        delugeLoginBody: DelugeJsonRPCInput
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

}