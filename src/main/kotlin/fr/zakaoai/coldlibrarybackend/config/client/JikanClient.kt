package fr.zakaoai.coldlibrarybackend.config.client

import net.sandrohc.jikan.Jikan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JikanClient {
    @Bean
    fun jikan(): Jikan = Jikan()
}