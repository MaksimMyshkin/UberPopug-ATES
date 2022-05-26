package com.example.accounting.client

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EmailClient {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun send(email: String, text: String) {
        logger.info("Отправлено письмо. email: $email. Текст: $text")
    }
}
