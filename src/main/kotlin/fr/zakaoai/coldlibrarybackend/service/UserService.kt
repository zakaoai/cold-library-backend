package fr.zakaoai.coldlibrarybackend.service

import fr.zakaoai.coldlibrarybackend.infrastructure.db.entities.User
import fr.zakaoai.coldlibrarybackend.infrastructure.db.services.UserRepository
import fr.zakaoai.coldlibrarybackend.model.mapper.toUserDTO
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun getCurrentUser() = ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .flatMap(userRepository::findById)
        .map(User::toUserDTO)

    fun updateCurrentUserMalUserName(malUsername: String) = ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .flatMap(userRepository::findById)
        .map { it.copy(malUsername = malUsername) }
        .flatMap(userRepository::save)
        .map(User::toUserDTO)

    fun getAllUser() = userRepository.findAll().map(User::toUserDTO)

}