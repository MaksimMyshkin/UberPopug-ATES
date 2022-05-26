package com.example.accounting.consumer.event

import java.util.*

data class TaskCreated(
    val publicId: UUID,
    val title: String,
    val description: String,
    val assigneePublicId: UUID,
)
