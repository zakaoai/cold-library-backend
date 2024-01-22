package fr.zakaoai.coldlibrarybackend.infrastructure.implementation

import fr.zakaoai.coldlibrarybackend.infrastructure.DelugeTorrentClient
import fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.CacheConfig
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import kotlin.random.Random

@Service
@CacheConfig
class DelugeTorrentImpl(@Qualifier("webClient") private val webClient: WebClient) : DelugeTorrentClient {

    fun connect() = webClient.post()
        .uri("/json")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(
            DelugeJsonRPCInput(
                "auth.login",
                listOf("deluge"),
                Random.nextInt(0, Int.MAX_VALUE)
            )
        )
        .retrieve()

        .bodyToMono(AuthLoginResponse::class.java)


    override fun downloadTorrent(torrentFile: String, downloadLocation: String) =
        webClient.post()
            .uri("/json")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                DelugeJsonRPCInput(
                    "core.add_torrent_url",
                    listOf(torrentFile, AddTorrentConfiguration(downloadLocation)),
                    Random.nextInt(0, Int.MAX_VALUE)
                )
            )
            .retrieve()
            .bodyToMono(AddTorrentResponse::class.java)

    override fun getDownloadTorrentStatus(hash: String): Mono<GetTorrentStatusResponse> =
        webClient.post()
            .uri("/json")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                DelugeJsonRPCInput(
                    "core.get_torrent_status",
                    listOf(hash, listOf("hash","download_location","progress")),
                    Random.nextInt(0, Int.MAX_VALUE)
                )
            )
            .retrieve()
            .bodyToMono(GetTorrentStatusResponse::class.java)

    override fun getMultipleDownloadTorrentStatus(hashs: List<String>): Mono<GetMultipleTorrentStatusResponse> =
        webClient.post()
            .uri("/json")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                DelugeJsonRPCInput(
                    "core.get_torrents_status",
                    listOf(hashMapOf(Pair("hash",hashs)), listOf("hash","download_location","progress")),
                    Random.nextInt(0, Int.MAX_VALUE)
                )
            )
            .retrieve()
            .bodyToMono(GetMultipleTorrentStatusResponse::class.java)

}