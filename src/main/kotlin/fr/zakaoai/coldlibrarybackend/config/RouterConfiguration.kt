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
        requestHandler: RequestHandler,
        logHandler: LogHandler
    ) =
        router {
            "seasons".nest {
                GET("", animeHandler::getSeasons)
                GET("{year}/{season}/{page}", animeHandler::searchAnimeBySeason)
            }
            "/anime".nest {
                GET("", animeHandler::getAllAnime)
                GET("{malId}", animeHandler::findByMalId)
                GET("{malId}/update", animeHandler::updateByMalId)
                POST("{malId}", animeHandler::saveAnime)
                DELETE("{malId}", animeHandler::deleteAnime)
                GET("search/{search}", animeHandler::searchAnime)
                PUT("{malId}/storage_state", animeHandler::updateStorageState)
                PUT("{malId}/last_avaible_episode", animeHandler::updateLastAvaibleEpisode)
                PUT("{malId}/is_complete", animeHandler::updateIsComplete)
                PUT("{malId}/is_downloading", animeHandler::updateIsDownloading)
            }
            "/anime/{malId}/episodes".nest {
                GET("", animeEpisodeHandler::findByMalId)
                GET("/{episodeNumber}", animeEpisodeHandler::findByMalIdAndEpisodeNumber)
                DELETE("", animeEpisodeHandler::deleteByMalId)
                DELETE("/{episodeNumber}", animeEpisodeHandler::deleteByMalIdAndEpisodeNumber)
            }
            GET("/cache/clearAllCaches", cacheHandler::clearAllCaches)
            "/torrent".nest {
                GET("/updateDelugeTorrent", delugeTorrentHandler::updateAllTorrent)
                GET("", animeTorrentHandler::getAllTrackedAnime)
                GET("{malId}", animeTorrentHandler::getTrackedAnime)
                PATCH("{malId}", animeTorrentHandler::updateTrackedAnime)
                DELETE("{malId}", animeTorrentHandler::deleteTrackedAnime)
                POST("{malId}", animeTorrentHandler::createTrackedAnime)

            }
            "/torrent/{malId}/episodes".nest {
                GET("", animeEpisodeTorrentHandler::findByMalId)
                GET("{episodeNumber}/alternate", animeEpisodeTorrentHandler::searchAlternate)
                GET("{episodeNumber}/update", animeEpisodeTorrentHandler::update)
                PUT("{episodeNumber}", animeEpisodeTorrentHandler::replace)
                DELETE("{episodeNumber}", animeEpisodeTorrentHandler::delete)
                GET("{episodeNumber}/deluge", delugeTorrentHandler::downloadTorrent)
                GET("{episodeNumber}/deluge/update", delugeTorrentHandler::updateTorrent)
                GET("scan", animeEpisodeTorrentHandler::scanAll)
                GET("scanPack", animeEpisodeTorrentHandler::scanPackage)
                GET("scanNext", animeEpisodeTorrentHandler::scanNext)
            }
            "user".nest {
                GET("animelist", myAnimeListHandler::getUserAnimeList)
                GET("", userHandler::getCurrentUser)
                GET("all", userHandler::getAllUser)
                PUT("malUsername", userHandler::updateCurrentUserMalUsername)
            }
            "request".nest {
                POST("", requestHandler::createRequest)
                GET("me", requestHandler::getMyRequest)
                GET("assigned", requestHandler::getMyAssignedRequest)
                GET("all", requestHandler::getAllRequest)
                POST("{requestId}", requestHandler::updateRequest)
            }
            "log".nest {
                GET("", logHandler::getAllLogs)
                GET("{userId}", logHandler::getLogsByUser)
            }
        }
}