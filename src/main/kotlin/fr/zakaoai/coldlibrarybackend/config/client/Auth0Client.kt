package fr.zakaoai.coldlibrarybackend.config.client

import com.auth0.client.auth.AuthAPI
import com.auth0.client.mgmt.ManagementAPI
import com.auth0.net.TokenRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Auth0Client {

    @Value("\${auth0.management.domain}")
    lateinit var domain: String

    @Value("\${auth0.management.clientId}")
    lateinit var clientId: String

    @Value("\${auth0.management.clientSecret}")
    lateinit var clientSecret: String

    @Value("\${auth0.management.audience}")
    lateinit var audience: String

    @Bean
    fun auth0AuthenticationApi(): AuthAPI = AuthAPI.newBuilder(domain, clientId, clientSecret).build()

    @Bean
    fun apiTokenRequest(authAPI: AuthAPI): TokenRequest = authAPI.requestToken(audience)

    @Bean
    fun auth0ManagementApi(tokenRequest: TokenRequest): ManagementAPI =
        ManagementAPI.newBuilder(domain, tokenRequest.execute().body.accessToken).build()

}