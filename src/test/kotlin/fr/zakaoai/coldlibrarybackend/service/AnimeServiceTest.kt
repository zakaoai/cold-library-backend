package fr.zakaoai.coldlibrarybackend.service


import fr.zakaoai.coldlibrarybackend.infrastructure.JikanApiService
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeInServerRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.AnimeTorrentRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class AnimeServiceTest {


    @MockK
    lateinit var animeInServerRepository: AnimeInServerRepository

    @MockK
    lateinit var animeRepository: AnimeRepository

    @MockK
    lateinit var animeTorrentRepository: AnimeTorrentRepository

    @MockK
    lateinit var jikanService: JikanApiService

    @InjectMockKs
    lateinit var animeService: AnimeService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun getListRdq_shouldReturnEmptyList_WhenRdqRepositoryReturnEmptyList() {

        every { animeInServerRepository.findAllWithAnimeInformation() } returns Flux.empty()


        val result = animeService.getAllAnime()
        StepVerifier.create(result).expectComplete()

    }

}