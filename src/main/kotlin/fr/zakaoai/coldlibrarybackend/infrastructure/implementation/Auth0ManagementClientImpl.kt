package fr.zakaoai.coldlibrarybackend.infrastructure.implementation


import com.auth0.client.mgmt.ManagementApi
import com.auth0.client.mgmt.core.ClientOptions
import fr.zakaoai.coldlibrarybackend.infrastructure.Auth0ManagementClient
import org.springframework.stereotype.Service

@Service
class Auth0ManagementClientImpl(val clientOptions: ClientOptions) :
    Auth0ManagementClient {

    override fun getCurrentUser(userId: String) =
        ManagementApi(clientOptions).users().get(userId)
}