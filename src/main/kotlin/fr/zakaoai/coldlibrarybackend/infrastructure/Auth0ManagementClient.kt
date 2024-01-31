package fr.zakaoai.coldlibrarybackend.infrastructure

import com.auth0.json.mgmt.users.User
import reactor.core.publisher.Mono

interface Auth0ManagementClient {

    fun getCurrentUser(userId: String): Mono<User>
}