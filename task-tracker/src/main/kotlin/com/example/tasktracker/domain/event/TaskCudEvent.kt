package com.example.tasktracker.domain.event

import com.example.tasktracker.domain.Task
import java.util.*

open class CudEvent(val aggregateId: Long)

class TaskCreated(
    id: Long,
    val title: String,
    val description: String,
    val assigneePublicId: UUID,
    val status: Task.Status
) : CudEvent(id) {
    constructor(task: Task) : this(task.id!!, task.title, task.description, task.assigneePublicId, task.status)
}
