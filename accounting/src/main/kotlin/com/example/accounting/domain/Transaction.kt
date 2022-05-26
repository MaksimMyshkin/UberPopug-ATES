package com.example.accounting.domain

import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
class Transaction(
    val amountChange: BigDecimal,
    val description: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreationTimestamp
    lateinit var timestamp: ZonedDateTime
}
