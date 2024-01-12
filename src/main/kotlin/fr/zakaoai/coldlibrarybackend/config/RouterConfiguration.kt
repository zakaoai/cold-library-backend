package fr.zakaoai.coldlibrarybackend.config

import fr.zakaoai.coldlibrarybackend.handler.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router


@Configuration
class RouterConfiguration {

    @Bean
    fun routes(
        animeHandler: AnimeHandler,
        cacheHandler: CacheHandler,
//        animeEpisodeHandler: AnimeEpisodeHandler,
//        trackedAnimeTorrentHandler: TrackedAnimeTorrentHandler,
//        animeEpisodeTorrentHandler: AnimeEpisodeTorrentHandler,
    ) =
        router {
            "/anime".nest {
                GET("", animeHandler::getAllAnime)
                GET("{id}", animeHandler::findByMalId)
                GET("{id}/update", animeHandler::updateByMalId)
                POST("{id}", animeHandler::saveAnime)
                DELETE("{id}", animeHandler::deleteAnime)
                GET("search/{search}", animeHandler::searchAnime)
                PUT("{id}/storage_state", animeHandler::updateStorageState)
                PUT("{id}/last_avaible_episode", animeHandler::updateLastAvaibleEpisode)
                PUT("{id}/is_complete", animeHandler::updateIsComplete)
            }
//            "/anime/{id}/episodes".nest {
//                GET("", animeEpisodeHandler::findByMalId)
//                DELETE("", animeEpisodeHandler::deleteByMalId)
//            }
//            GET("/cache/clearAllCaches", cacheHandler::clearAllCaches)
//            "/torrent".nest {
//                GET("", trackedAnimeTorrentHandler::getAllTrackedAnime)
//                GET("{id}", trackedAnimeTorrentHandler::getTrackedAnime)
//                PATCH("{id}", trackedAnimeTorrentHandler::updateTrackedAnime)
//                DELETE("{id}", trackedAnimeTorrentHandler::deleteTrackedAnime)
//                POST("{id}", trackedAnimeTorrentHandler::createTrackedAnime)
//            }
//            "/torrent/{id}/episodes".nest {
//                GET("", animeEpisodeTorrentHandler::getAnimeEpisodeTorrents)
//                GET("{episodeNumber}/alternate", animeEpisodeTorrentHandler::searchAlternateEpisodeTorrent)
//                GET("{episodeNumber}/update", animeEpisodeTorrentHandler::updateEpisodeTorrent)
//                GET("{episodeNumber}/search", animeEpisodeTorrentHandler::searchEpisodeTorrent)
//                PUT("{episodeNumber}", animeEpisodeTorrentHandler::replaceEpisodeTorrent)
//                DELETE("{episodeNumber}", animeEpisodeTorrentHandler::deleteEpisodeTorrent)
//                GET("scan", animeEpisodeTorrentHandler::scanEpisodeTorrent)
//                GET("scanPack", animeEpisodeTorrentHandler::scanPackageTorrent)
//                GET("scanNext", animeEpisodeTorrentHandler::scanNextTorrent)
//            }
        }
}