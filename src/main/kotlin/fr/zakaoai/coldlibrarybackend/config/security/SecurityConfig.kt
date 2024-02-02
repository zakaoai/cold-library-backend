package fr.zakaoai.coldlibrarybackend.config.security


import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    lateinit var issuer: String

    @Value("\${auth0.audience}")
    lateinit var audience: String


    @Bean
    fun corsConfiguration(): CorsConfigurationSource {
        val config = CorsConfiguration()

        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")

        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
        return source
    }

    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        corsConfigurationSource: CorsConfigurationSource
    ): SecurityWebFilterChain = http {
        cors { }
        authorizeExchange {
            authorize("/anime/**", hasAuthority("admin"))
            authorize("/torrent/**", hasAuthority("admin"))
            authorize(anyExchange, hasAuthority("admin"))
        }
        oauth2ResourceServer {
            jwt {
                jwtAuthenticationConverter = makePermissionsConverter()
                jwtDecoder = jwtDecoder()
            }
        }
    }

    fun jwtDecoder(): ReactiveJwtDecoder {
        val jwtDecoder = ReactiveJwtDecoders.fromOidcIssuerLocation(issuer) as NimbusReactiveJwtDecoder
        val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(audience)
        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
        jwtDecoder.setJwtValidator(withAudience)
        return jwtDecoder
    }


    private fun makePermissionsConverter(): ReactiveJwtAuthenticationConverter {
        val jwtAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        jwtAuthoritiesConverter.setAuthoritiesClaimName("permissions")
        jwtAuthoritiesConverter.setAuthorityPrefix("")
        val reactiveJwtConverter = ReactiveJwtGrantedAuthoritiesConverterAdapter(jwtAuthoritiesConverter)
        val jwtAuthConverter = ReactiveJwtAuthenticationConverter()
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(reactiveJwtConverter)
        return jwtAuthConverter
    }

}