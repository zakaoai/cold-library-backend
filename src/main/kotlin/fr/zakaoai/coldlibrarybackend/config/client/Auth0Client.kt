package fr.zakaoai.coldlibrarybackend.config.client

import com.auth0.client.mgmt.ManagementApi
import com.auth0.client.mgmt.core.ClientOptions
import com.auth0.client.mgmt.core.Environment
import com.auth0.client.mgmt.core.OAuthTokenSupplier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Supplier


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
    fun oAuthTokenSupplier(): OAuthTokenSupplier =
        OAuthTokenSupplier(clientId, clientSecret, "https://$domain", audience)

    @Bean
    fun clientOptions(tokenSupplier: OAuthTokenSupplier): ClientOptions = ClientOptions.builder()
        .environment(Environment.custom(audience))
        .addHeader("Authorization", Supplier { "Bearer " + tokenSupplier.get() })
        .build();

    @Bean
    fun auth0ManagementApi(clientOptions: ClientOptions): ManagementApi = ManagementApi(clientOptions)


}