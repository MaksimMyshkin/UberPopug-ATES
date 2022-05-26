package com.example.accounting.consumer.event

import java.util.*

data class TaskAssigned(
    val publicId: UUID,
    val assigneePublicId: UUID
)
