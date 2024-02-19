package fr.zakaoai.coldlibrarybackend.config.filter

import fr.zakaoai.coldlibrarybackend.infrastructure.Auth0ManagementClient
import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.User
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.session.ReactiveMapSessionRepository
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession
import org.springframework.web.server.WebFilter
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.ConcurrentHashMap


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
                    null -> ReactiveSecurityContextHolder.getContext().flatMap {
                        auth0ManagementClient.getCurrentUser(it.authentication.name)
                    }.flatMap {
                        userRepository.findById(it.id)
                            .switchIfEmpty { userRepository.save(User(it.id, it.name, it.email, null, true)) }
                    }
                        .doOnNext { webSession.attributes["user"] = it }

                    else -> webSession.getAttribute<User>("user").toMono()
                }
            }
            .then(webFilterChain.filter(serverWebExchange))
    }
}