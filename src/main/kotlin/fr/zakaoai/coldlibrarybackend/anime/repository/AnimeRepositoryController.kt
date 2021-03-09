package fr.zakaoai.coldlibrarybackend.anime.repository

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.toModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/animeRepo"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AnimeRepositoryController(private val repo: AnimeRepository) {

    @GetMapping
    fun getAll() = repo.findAll()

    @GetMapping("/{malId}")
    fun getByLastName(@PathVariable malId: Int) = repo.findByMalId(malId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody employee: AnimeDTO) = repo.save(employee.toModel())

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = repo.deleteById(id)

}