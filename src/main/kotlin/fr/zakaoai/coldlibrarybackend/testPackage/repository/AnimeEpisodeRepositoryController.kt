package fr.zakaoai.coldlibrarybackend.testPackage.repository

import fr.zakaoai.coldlibrarybackend.anime.DTO.AnimeEpisodeDTO
import fr.zakaoai.coldlibrarybackend.anime.DTO.toModel
import fr.zakaoai.coldlibrarybackend.anime.repository.AnimeEpisodeRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/AnimeEpisodeRepo"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AnimeEpisodeRepositoryController(private val repo: AnimeEpisodeRepository) {

    @GetMapping
    fun getAll() = repo.findAll()

    @GetMapping("/{malId}")
    fun getByLastName(@PathVariable malId: Int) = repo.findByMalId(malId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody animeEpisodeDTO: AnimeEpisodeDTO) = repo.save(animeEpisodeDTO.toModel())

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = repo.deleteById(id)

}