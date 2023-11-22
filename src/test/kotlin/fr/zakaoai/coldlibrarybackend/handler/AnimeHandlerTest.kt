package fr.zakaoai.coldlibrarybackend.handler

import com.ninjasquad.springmockk.MockkBean
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import fr.zakaoai.coldlibrarybackend.service.AnimeService
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.kotlin.core.publisher.toFlux

@SpringBootTest
class AnimeHandlerTest {

    @Autowired
    lateinit var routerFunction: RouterFunction<ServerResponse>

    lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build()
    }

    @MockkBean
    lateinit var animeService: AnimeService

    @Test
    fun getAllAnime_ShouldReturnEmptyListOfAnime_WhenAnimeServiceReturnEmptyListOfAnime() {

        every { animeService.getAllAnime() } returns emptyList<AnimeDTO>().toFlux()
        webTestClient.get()
            .uri("/anime")
            .exchange()
            .expectBody(object : ParameterizedTypeReference<List<AnimeDTO>>() {})
            .isEqualTo(
                emptyList()
            )
    }
}