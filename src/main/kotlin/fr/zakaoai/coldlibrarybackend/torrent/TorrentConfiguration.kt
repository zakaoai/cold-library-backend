package fr.zakaoai.coldlibrarybackend.torrent

import de.kaysubs.tracker.nyaasi.NyaaSiApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TorrentConfiguration {

    @Bean
    fun nyaaSiApi(): NyaaSiApi = NyaaSiApi.getNyaa();

}