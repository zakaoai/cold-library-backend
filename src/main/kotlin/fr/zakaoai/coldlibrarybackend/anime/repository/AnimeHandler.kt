package fr.zakaoai.coldlibrarybackend.anime.repository

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.ErrorMessage

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class AnimeHandler(private val service: AnimeRepositoryServiceCorr) {
    private val logger = LoggerFactory.getLogger(AnimeHandler::class.java)

    suspend fun findAll(request: ServerRequest): ServerResponse {
        val users = service.findAll()
        return ServerResponse.ok().json().bodyAndAwait(users)
    }

    suspend fun search(request: ServerRequest): ServerResponse {
        val criterias = request.queryParams()
        return when {
            criterias.isEmpty() -> ServerResponse.badRequest().json()
                .bodyValueAndAwait(ErrorMessage("Search must have query params"))
            criterias.contains("userId") -> {
                val criteriaValue = criterias.getFirst("userId")?.toInt()
                if (criteriaValue == null) {
                    ServerResponse.badRequest().json()
                        .bodyValueAndAwait(ErrorMessage("Incorrect search criteria value"))
                } else {
                    ServerResponse.ok().json().bodyAndAwait(service.findByMalId(criteriaValue))
                }
            }
            else -> ServerResponse.badRequest().json().bodyValueAndAwait(ErrorMessage("Incorrect search criteria"))
        }
    }

    suspend fun findPost(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLongOrNull()
        return if (id == null) {
            ServerResponse.badRequest().json().bodyValueAndAwait(ErrorMessage("`id` must be numeric"))
        } else {
            val user = service.findById(id)
            if (user == null) ServerResponse.notFound().buildAndAwait()
            else ServerResponse.ok().json().bodyValueAndAwait(user)
        }
    }

    suspend fun addPost(request: ServerRequest): ServerResponse {
        val newPost = try {
            request.bodyToMono<AnimeDTO>().awaitFirstOrNull()
        } catch (e: Exception) {
            logger.error("Decoding body error", e)
            null
        }
        return if (newPost == null) {
            ServerResponse.badRequest().json().bodyValueAndAwait(ErrorMessage("Invalid body"))
        } else {
            val user = service.addOne(newPost)
            if (user == null) ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).json().bodyValueAndAwait(
                ErrorMessage("Internal error")
            )
            else ServerResponse.status(HttpStatus.CREATED).json().bodyValueAndAwait(user)
        }
    }

    suspend fun updatePost(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLongOrNull()
        return if (id == null) {
            ServerResponse.badRequest().json().bodyValueAndAwait(ErrorMessage("`id` must be numeric"))
        } else {
            val updateUser = try {
                request.bodyToMono<AnimeDTO>().awaitFirstOrNull()
            } catch (e: Exception) {
                logger.error("Decoding body error", e)
                null
            }
            if (updateUser == null) {
                ServerResponse.badRequest().json().bodyValueAndAwait(ErrorMessage("Invalid body"))
            } else {
                val user = service.updateOne(id, updateUser)
                if (user == null) ServerResponse.status(HttpStatus.NOT_FOUND).json()
                    .bodyValueAndAwait(ErrorMessage("Resource $id not found"))
                else ServerResponse.status(HttpStatus.OK).json().bodyValueAndAwait(user)
            }
        }
    }

    suspend fun deletePost(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLongOrNull()
        return if (id == null) {
            ServerResponse.badRequest().json().bodyValueAndAwait(ErrorMessage("`id` must be numeric"))
        } else {
            if (service.deleteOne(id)) ServerResponse.noContent().buildAndAwait()
            else ServerResponse.status(HttpStatus.NOT_FOUND).json()
                .bodyValueAndAwait(ErrorMessage("Resource $id not found"))
        }
    }
}