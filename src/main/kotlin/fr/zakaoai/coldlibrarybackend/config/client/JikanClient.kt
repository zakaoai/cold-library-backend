package fr.zakaoai.coldlibrarybackend.config.client

import net.sandrohc.jikan.Jikan
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JikanClient {

    @Value("\${jikan.host}")
    lateinit var jikanUrl: String

    @Bean
    fun jikan(): Jikan = Jikan.JikanBuilder().baseUrl(jikanUrl).build()
}