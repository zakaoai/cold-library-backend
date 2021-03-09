package fr.zakaoai.coldlibrarybackend.anime.repository

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.toModel
import fr.zakaoai.coldlibrarybackend.anime.repository.entity.Anime
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service

@Service
class AnimeRepositoryServiceCorr(private val repo: AnimeRepository) {

    suspend fun findAll() = repo.findAll().asFlow()
    suspend fun findById(id: Long) = repo.findById(id).awaitFirstOrNull()
    suspend fun findByMalId(animeId: Int) = repo.findByMalId(animeId).asFlow()
    suspend fun addOne(anime: AnimeDTO) = repo.save(anime.toModel()).awaitFirstOrNull()
    suspend fun updateOne(id: Long, post: AnimeDTO): Anime? {
        val existingPost = findById(id)
        return if (existingPost != null) repo.save(post.toModel(withId = id)).awaitFirstOrNull() else null
    }

    suspend fun deleteOne(id: Long): Boolean {
        val existingAnime = findById(id)
        return if (existingAnime != null) {
            repo.delete(existingAnime).awaitFirstOrNull()
            true
        } else false
    }

}