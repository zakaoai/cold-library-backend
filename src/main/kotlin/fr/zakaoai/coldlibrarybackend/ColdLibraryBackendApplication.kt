package fr.zakaoai.coldlibrarybackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication(exclude = [ReactiveSecurityAutoConfiguration::class] )
@EnableCaching
class ColdLibraryBackendApplication

fun main(args: Array<String>) {
	runApplication<ColdLibraryBackendApplication>(*args)
}
