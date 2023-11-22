package fr.zakaoai.coldlibrarybackend.config

import net.sandrohc.jikan.Jikan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AnimeConfiguration {
    @Bean
    fun jikan(): Jikan = Jikan()
}