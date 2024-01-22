package fr.zakaoai.coldlibrarybackend.config.webclient

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ReactorResourceFactory
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MyAnimeListClient {

    val myAnimeListUrl = "https://api.myanimelist.net"

    @Bean("MALWebClient")
    fun authWebClient(resourceFactory: ReactorResourceFactory) = WebClient.builder().baseUrl(myAnimeListUrl)
        .defaultHeader("X-MAL-CLIENT-ID", "13542614067bb3cfdcaac71f933af8ea").build()
}