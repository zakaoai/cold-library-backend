package fr.zakaoai.coldlibrarybackend.handler

import com.auth0.json.auth.TokenHolder
import com.auth0.net.Response
import com.auth0.net.TokenRequest
import com.ninjasquad.springmockk.MockkBean
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import fr.zakaoai.coldlibrarybackend.service.AnimeService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.kotlin.core.publisher.toFlux


@SpringBootTest
@TestPropertySource(properties = ["spring.liquibase.enabled=false"])
class AnimeHandlerTest {

    @TestConfiguration
    class TestConfig {
        @Bean
        @Primary
        fun tokenRequest(): TokenRequest {
            val std= mockk<TokenRequest>()
            every { std.execute() } returns (object : Response<TokenHolder> {
                override fun getHeaders(): MutableMap<String, String> = HashMap()
                override fun getBody(): TokenHolder = TokenHolder("",null,null,null,1L,null,null)
                override fun getStatusCode(): Int = 200
            })
            return std
        }
    }

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
    @WithMockUser
    fun getAllAnime_ShouldReturnEmptyListOfAnime_WhenAnimeServiceReturnEmptyListOfAnime() {

        every { animeService.getAllAnime() } returns emptyList<AnimeDTO>().toFlux()


        webTestClient.get()
            .uri("/anime")
            .exchange()
            .expectStatus().isOk
    }
}