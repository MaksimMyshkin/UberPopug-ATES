package com.example.tasktracker.controller.dto

import com.example.tasktracker.domain.Task
import java.util.UUID

data class TaskViewDto(
    val id: Long,
    val title: String,
    val description: String,
    val assigneePublicId: UUID,
    val status: Task.Status
)
