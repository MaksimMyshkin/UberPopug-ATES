package com.example.tasktracker.domain.event

import java.util.UUID

open class DomainEvent(val publicId: UUID)

class TaskCreated(
    publicId: UUID,
    val title: String,
    val description: String,
    val assigneePublicId: UUID,
) : DomainEvent(publicId)

class TaskAssigned(
    publicId: UUID,
    val assigneePublicId: UUID
) : DomainEvent(publicId)

class TaskCompleted(
    publicId: UUID
) : DomainEvent(publicId)
