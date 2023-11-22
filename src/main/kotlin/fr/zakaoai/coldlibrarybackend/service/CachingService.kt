package fr.zakaoai.coldlibrarybackend.service

import org.springframework.cache.CacheManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

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
                    cacheManager.getCache(cacheName)!!.clear()
                }
    }

    @Scheduled(fixedRate = 3600000)
    fun evictAllcachesAtIntervals() {
        evictAllCaches()
    }
}