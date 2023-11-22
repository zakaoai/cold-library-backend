package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeEpisodeRepository
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeRepository
import fr.zakaoai.coldlibrarybackend.infrastructure.JikanAPIService
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.TrackedAnimeTorrentRepository
import fr.zakaoai.coldlibrarybackend.model.dto.response.AnimeDTO
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class AnimeServiceTest{

    @MockK
    lateinit var repo: AnimeRepository
    @MockK
    lateinit var episodeRepo: AnimeEpisodeRepository
    @MockK
    lateinit var jikanService: JikanAPIService
    @MockK
    lateinit var trackedAnimeTorrentRepository: TrackedAnimeTorrentRepository


    @InjectMockKs
    lateinit var animeService: AnimeService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun getListRdq_shouldReturnEmptyList_WhenRdqRepositoryReturnEmptyList(){

        every { repo.findAll() } returns Flux.empty()


        val result = animeService.getAllAnime()
        StepVerifier.create(result).expectComplete()

    }

}