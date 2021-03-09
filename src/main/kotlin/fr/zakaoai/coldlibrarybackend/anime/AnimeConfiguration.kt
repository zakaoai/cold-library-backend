package fr.zakaoai.coldlibrarybackend.anime

import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeHandler
import net.sandrohc.jikan.Jikan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AnimeConfiguration {

    @Bean
    fun animeRoute(animeHandler: AnimeHandler) = coRouter {
        GET("/animeH", animeHandler::findAll)
        GET("/animeH/search", animeHandler::search)
        GET("/animeH/{id}", animeHandler::findPost)
        POST("/animeH", animeHandler::addPost)
        PUT("/animeH/{id}", animeHandler::updatePost)
        DELETE("/animeH/{id}", animeHandler::deletePost)
    }

    @Bean
    fun jikan() : Jikan = Jikan()

}