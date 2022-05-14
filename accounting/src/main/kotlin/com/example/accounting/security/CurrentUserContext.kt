package com.example.accounting.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import org.springframework.stereotype.Component
import java.util.UUID


@Component
class CurrentUserContext {

    val publicId: UUID
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            check(authentication is BearerTokenAuthentication)
            return authentication.tokenAttributes["public_id"] as UUID
        }

    fun hasAnyRole(vararg roles: String): Boolean {
        val roleAuthorities = roles.map { "ROLE_${it.uppercase()}" }
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.authorities.any { roleAuthorities.contains(it.authority) }
    }
}
