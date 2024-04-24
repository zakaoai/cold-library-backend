package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.extension.logger
import org.springframework.cache.CacheManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class CachingService(private val cacheManager: CacheManager) {


    fun evictSingleCacheValue(cacheName: String, cacheKey: String) {
        cacheManager.getCache(cacheName)!!.evict(cacheKey)
    }

    fun evictAllCacheValues(cacheName: String) {
        cacheManager.getCache(cacheName)!!.clear()
    }

    fun evictAllCaches() {
        cacheManager.cacheNames.stream()
            .forEach { cacheName: String ->
                logger().info("Cache {} cleared", cacheName)
                cacheManager.getCache(cacheName)!!.clear()
            }
    }

    @Scheduled(fixedRate = 3600000)
    fun evictAllcachesAtIntervals() {
        evictAllCaches()
        logger().info("Cache clear at {}", SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date()))
    }
}