package fr.zakaoai.coldlibrarybackend.testPackage.controller

import fr.zakaoai.coldlibrarybackend.torrent.repository.TrackedAnimeTorrentRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/repo/torrent"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TrackedAnimeTorrentRepositoryController(private val repo: TrackedAnimeTorrentRepository) {

    @GetMapping
    fun getAll() = repo.findAll()

    @GetMapping("/{malId}")
    fun getByLastName(@PathVariable malId: Int) = repo.findByMalId(malId)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = repo.deleteById(id)

}
