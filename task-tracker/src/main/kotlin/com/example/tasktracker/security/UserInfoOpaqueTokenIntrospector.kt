package com.example.tasktracker.security

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import org.springframework.stereotype.Component
import java.net.URI
import java.util.*

@Component
class UserInfoOpaqueTokenIntrospector(
    private val properties: OAuth2ResourceServerProperties,
    restTemplateBuilder: RestTemplateBuilder
) : OpaqueTokenIntrospector {

    private val delegate = NimbusOpaqueTokenIntrospector(properties.opaquetoken.introspectionUri,
        properties.opaquetoken.clientId, properties.opaquetoken.clientSecret)

    private val restTemplate = restTemplateBuilder.build()

    override fun introspect(token: String): OAuth2AuthenticatedPrincipal {
        val principal = delegate.introspect(token)
        val account = findAccount(token)

        val authorities = principal.authorities + SimpleGrantedAuthority("ROLE_${account.role.uppercase()}")
        val attributes = principal.attributes + ("public_id" to account.publicId)
        return OAuth2IntrospectionAuthenticatedPrincipal(attributes, authorities)
    }

    private fun findAccount(token: String): Account {
        val url = URI(properties.opaquetoken.introspectionUri).resolve("/accounts/current.json")
        val httpHeaders = HttpHeaders()
        httpHeaders[HttpHeaders.AUTHORIZATION] = "Bearer $token"

        val responseBody = restTemplate.exchange(url, HttpMethod.GET, HttpEntity<Void>(httpHeaders),
            Account::class.java).body
        return checkNotNull(responseBody) { "Не удалось получить данные аккаунта по токену: $token" }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Account(val publicId: UUID, val role: String)
}
