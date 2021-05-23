package fr.zakaoai.coldlibrarybackend

import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class ColdLibraryBackendApplication

fun main(args: Array<String>) {
    val app = SpringApplication(ColdLibraryBackendApplication::class.java)
    app.webApplicationType = WebApplicationType.REACTIVE
    app.run(*args)
}