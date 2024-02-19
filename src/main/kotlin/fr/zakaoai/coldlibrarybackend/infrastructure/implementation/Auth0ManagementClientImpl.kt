package fr.zakaoai.coldlibrarybackend.infrastructure.implementation

import com.auth0.client.mgmt.ManagementAPI
import com.auth0.client.mgmt.filter.UserFilter
import com.auth0.net.TokenRequest
import fr.zakaoai.coldlibrarybackend.infrastructure.Auth0ManagementClient
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toMono

@Service
class Auth0ManagementClientImpl(val tokenRequest: TokenRequest, val auth0ManagementApi: ManagementAPI) :
    Auth0ManagementClient {

    fun renewedManagementapi(): ManagementAPI {
        auth0ManagementApi.setApiToken(tokenRequest.execute().body.accessToken)
        return auth0ManagementApi
    }

    override fun getCurrentUser(userId: String) =
        renewedManagementapi().users().get(userId, UserFilter()).executeAsync().toMono()
            .map { it.body }
}