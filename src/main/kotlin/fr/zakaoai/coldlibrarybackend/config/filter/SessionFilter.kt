package fr.zakaoai.coldlibrarybackend.config.filter

import fr.zakaoai.coldlibrarybackend.infrastructure.Auth0ManagementClient
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.User
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.session.ReactiveMapSessionRepository
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession
import org.springframework.web.server.WebFilter
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.ConcurrentHashMap
import kotlin.jvm.optionals.getOrNull


@Configuration
@EnableSpringWebSession
class SessionFilter(val userRepository: UserRepository, val auth0ManagementClient: Auth0ManagementClient) {
    @Bean
    fun reactiveSessionRepository() = ReactiveMapSessionRepository(ConcurrentHashMap())

    @Bean
    fun filter(): WebFilter = WebFilter { serverWebExchange, webFilterChain ->
        serverWebExchange.session
            .flatMap { webSession ->
                when (webSession.getAttribute<User>("user")) {
                    null -> ReactiveSecurityContextHolder.getContext()
                        .mapNotNull(SecurityContext::getAuthentication)
                        .mapNotNull (Authentication::getName)
                        .mapNotNull (auth0ManagementClient::getCurrentUser)
                        .flatMap {

                        userRepository.findById(it.userId.orElseThrow())
                            .switchIfEmpty { userRepository.save(User(it.userId.orElseThrow(), it.name.orElseThrow(), it.email.orElseThrow(), null, true)) }
                    }
                        .doOnNext { webSession.attributes["user"] = it }

                    else -> webSession.getAttribute<User>("user").toMono()
                }
            }
            .then(webFilterChain.filter(serverWebExchange))
    }
}