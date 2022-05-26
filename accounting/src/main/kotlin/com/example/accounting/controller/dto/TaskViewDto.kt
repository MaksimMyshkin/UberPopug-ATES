package com.example.accounting.controller.dto

import java.math.BigDecimal
import java.util.*

data class TaskViewDto(
    val id: Long,
    val publicId: UUID,
    val debitedOnAssignmentAmount: BigDecimal,
    val creditedOnCompletionAmount: BigDecimal
)
