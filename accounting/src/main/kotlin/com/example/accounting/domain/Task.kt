package com.example.accounting.domain

import java.math.BigDecimal
import java.util.UUID
import javax.persistence.*
import kotlin.random.Random

@Entity
class Task(
    @Column(unique = true)
    val publicId: UUID,

    assigneePublicId: UUID
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val debitedOnAssignmentAmount: BigDecimal = BigDecimal.valueOf(Random.Default.nextLong(10, 21))

    val creditedOnCompletionAmount: BigDecimal = BigDecimal.valueOf(Random.Default.nextLong(20,41))

    var assigneePublicId = assigneePublicId
        private set

    fun assign(employeePublicId: UUID) {
        assigneePublicId = employeePublicId
    }
}
