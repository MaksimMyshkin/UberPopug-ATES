package com.example.tasktracker.consumer.event

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.util.UUID

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class AccountEvent(
    val eventName: String,
    val data: Data
) {
    class Data(
        val publicId: UUID,
        val fullName: String?,
    )
}
