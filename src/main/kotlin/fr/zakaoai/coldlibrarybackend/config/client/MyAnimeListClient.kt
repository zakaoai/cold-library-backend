package fr.zakaoai.coldlibrarybackend.config.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ReactorResourceFactory
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MyAnimeListClient {

    val myAnimeListUrl = "https://api.myanimelist.net"

    @Value("\${myanimelist.client-id}")
    lateinit var clientId: String

    @Bean("MALWebClient")
    fun authWebClient(resourceFactory: ReactorResourceFactory) = WebClient.builder().baseUrl(myAnimeListUrl)
        .defaultHeader("X-MAL-CLIENT-ID", clientId).build()
}