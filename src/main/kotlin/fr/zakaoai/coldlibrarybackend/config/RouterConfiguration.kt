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
        animeEpisodeHandler: AnimeEpisodeHandler,
        animeTorrentHandler: AnimeTorrentHandler,
        animeEpisodeTorrentHandler: AnimeEpisodeTorrentHandler,
        delugeTorrentHandler: DelugeTorrentHandler,
        myAnimeListHandler: MyAnimeListHandler,
        userHandler: UserHandler,
        requestHandler: RequestHandler
    ) =
        router {
            "seasons".nest {
                GET("", animeHandler::getSeasons)
                GET("{year}/{season}/{page}", animeHandler::searchAnimeBySeason)
            }
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
                PUT("{id}/is_downloading", animeHandler::updateIsDownloading)
            }
            "/anime/{id}/episodes".nest {
                GET("", animeEpisodeHandler::findByMalId)
                GET("/{episodeNumber}", animeEpisodeHandler::findByMalIdAndEpisodeNumber)
                DELETE("", animeEpisodeHandler::deleteByMalId)
                DELETE("/{episodeNumber}", animeEpisodeHandler::deleteByMalIdAndEpisodeNumber)
            }
//            GET("/cache/clearAllCaches", cacheHandler::clearAllCaches)
            "/torrent".nest {
                GET("/updateDelugeTorrent", delugeTorrentHandler::updateAllTorrent)
                GET("", animeTorrentHandler::getAllTrackedAnime)
                GET("{id}", animeTorrentHandler::getTrackedAnime)
                PATCH("{id}", animeTorrentHandler::updateTrackedAnime)
                DELETE("{id}", animeTorrentHandler::deleteTrackedAnime)
                POST("{id}", animeTorrentHandler::createTrackedAnime)

            }
            "/torrent/{id}/episodes".nest {
                GET("", animeEpisodeTorrentHandler::getAnimeEpisodeTorrents)
                GET("{episodeNumber}/alternate", animeEpisodeTorrentHandler::searchAlternateEpisodeTorrent)
                GET("{episodeNumber}/update", animeEpisodeTorrentHandler::updateEpisodeTorrent)
                PUT("{episodeNumber}", animeEpisodeTorrentHandler::replaceEpisodeTorrent)
                DELETE("{episodeNumber}", animeEpisodeTorrentHandler::deleteEpisodeTorrent)
                GET("{episodeNumber}/deluge", delugeTorrentHandler::downloadTorrent)
                GET("{episodeNumber}/deluge/update", delugeTorrentHandler::updateTorrent)
                GET("scan", animeEpisodeTorrentHandler::scanEpisodeTorrent)
                GET("scanPack", animeEpisodeTorrentHandler::scanPackageTorrent)
                GET("scanNext", animeEpisodeTorrentHandler::scanNextTorrent)
            }
            "user".nest {
                GET("animelist", myAnimeListHandler::getUserAnimeList)
                GET("", userHandler::getCurrentUser)
                GET("all",userHandler::getAllUser)
                PUT("malUsername", userHandler::updateCurrentUserMalUsername)
            }
            "request".nest {
                POST("",requestHandler::createRequest)
                GET("me", requestHandler::getMyRequest)
                GET("assigned", requestHandler::getMyAssignedRequest)
                GET("all",requestHandler::getAllRequest)
                POST("{id}",requestHandler::updateRequest)
            }
        }
}