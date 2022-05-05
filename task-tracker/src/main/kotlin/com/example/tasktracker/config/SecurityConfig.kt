package com.example.tasktracker.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/**").authenticated()
            .and()
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity>::opaqueToken)
        // Отключаем CSRF во избежание проблем
        http.csrf().disable()
    }
}
