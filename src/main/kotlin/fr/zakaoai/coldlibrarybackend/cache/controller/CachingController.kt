package fr.zakaoai.coldlibrarybackend.cache.controller

import fr.zakaoai.coldlibrarybackend.cache.service.CachingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(path = ["/cache"])
class CachingController {
    @Autowired
    var cachingService: CachingService? = null

    @GetMapping("clearAllCaches")
    fun clearAllCaches() {
        cachingService!!.evictAllCaches()
    }

}