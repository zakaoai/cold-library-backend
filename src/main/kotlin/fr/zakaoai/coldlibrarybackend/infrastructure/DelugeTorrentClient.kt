package fr.zakaoai.coldlibrarybackend.infrastructure

import fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge.AddTorrentResponse
import fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge.GetMultipleTorrentStatusResponse
import fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge.GetTorrentStatusResponse
import reactor.core.publisher.Mono

interface DelugeTorrentClient {

    fun downloadTorrent(torrentFile: String, downloadLocation: String): Mono<AddTorrentResponse>

    fun getDownloadTorrentStatus(hash: String): Mono<GetTorrentStatusResponse>

    fun getMultipleDownloadTorrentStatus(hashs: List<String>): Mono<GetMultipleTorrentStatusResponse>
}