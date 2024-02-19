package fr.zakaoai.coldlibrarybackend.config.client

import de.kaysubs.tracker.nyaasi.NyaaSiApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NyaaSiClient {

    @Bean
    fun nyaaSiApi(): NyaaSiApi = NyaaSiApi.getNyaa()

}