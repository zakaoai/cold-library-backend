package fr.zakaoai.coldlibrarybackend.infrastructure.implementation

import fr.zakaoai.coldlibrarybackend.infrastructure.DelugeTorrentClient
import fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.CacheConfig
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import kotlin.random.Random

@Service
@CacheConfig
class DelugeTorrentImpl(@Qualifier("webClient") private val webClient: WebClient) : DelugeTorrentClient {

    fun isConnected() = webClient.post()
        .uri("/json")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(
            DelugeJsonRPCInput(
                "web.connected",
                listOf(),
                Random.nextInt(0, Int.MAX_VALUE)
            )
        )
        .retrieve()

        .bodyToMono(ConnectedResponse::class.java)

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

    fun getHosts() = webClient.post()
        .uri("/json")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(
            DelugeJsonRPCInput(
                "web.get_hosts",
                listOf(),
                Random.nextInt(0, Int.MAX_VALUE)
            )
        )
        .retrieve()

        .bodyToMono(GetHostsResponse::class.java)

    fun webConnect(idHost: String) = webClient.post()
        .uri("/json")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(
            DelugeJsonRPCInput(
                "web.connect",
                listOf(idHost),
                Random.nextInt(0, Int.MAX_VALUE)
            )
        )
        .retrieve()

        .bodyToMono(WebConnectResponse::class.java)

    fun connectToHost() = connect().then(getHosts()).map { it.result?.get(0)?.get(0) as String }
        .flatMap(this::webConnect)

    fun connectIfNeeded() = isConnected().flatMap { if (it.result == false) connectToHost() else Mono.empty() }


    override fun downloadTorrent(torrentFile: String, downloadLocation: String) =
        connectIfNeeded().then(
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
        )

    override fun getDownloadTorrentStatus(hash: String): Mono<GetTorrentStatusResponse> =
        connectIfNeeded().then(
            webClient.post()
                .uri("/json")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(
                    DelugeJsonRPCInput(
                        "core.get_torrent_status",
                        listOf(hash, listOf("hash", "download_location", "progress")),
                        Random.nextInt(0, Int.MAX_VALUE)
                    )
                )
                .retrieve()
                .bodyToMono(GetTorrentStatusResponse::class.java)
        )

    override fun getMultipleDownloadTorrentStatus(hashs: List<String>): Mono<GetMultipleTorrentStatusResponse> =
        connectIfNeeded().then(
            webClient.post()
                .uri("/json")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(
                    DelugeJsonRPCInput(
                        "core.get_torrents_status",
                        listOf(hashMapOf(Pair("hash", hashs)), listOf("hash", "download_location", "progress")),
                        Random.nextInt(0, Int.MAX_VALUE)
                    )
                )
                .retrieve()
                .bodyToMono(GetMultipleTorrentStatusResponse::class.java)
        )

}