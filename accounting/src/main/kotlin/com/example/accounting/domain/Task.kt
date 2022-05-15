package com.example.accounting.domain

import java.math.BigDecimal
import java.util.UUID
import javax.persistence.*
import kotlin.random.Random

@Entity
class Task(
    @Column(unique = true)
    val publicId: UUID
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val debitedOnAssignmentAmount: BigDecimal = BigDecimal.valueOf(Random.Default.nextLong(-20, -9))
    val creditedOnCompletionAmount: BigDecimal = BigDecimal.valueOf(Random.Default.nextLong(20,41))
}
