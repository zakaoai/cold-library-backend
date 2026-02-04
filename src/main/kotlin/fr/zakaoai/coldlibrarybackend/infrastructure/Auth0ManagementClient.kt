package fr.zakaoai.coldlibrarybackend.infrastructure

import com.auth0.client.mgmt.types.GetUserResponseContent

interface Auth0ManagementClient {

    fun getCurrentUser(userId: String):GetUserResponseContent
}