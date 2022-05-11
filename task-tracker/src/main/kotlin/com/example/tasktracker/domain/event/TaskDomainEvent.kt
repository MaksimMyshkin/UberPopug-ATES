package com.example.tasktracker.domain.event

import java.util.UUID

open class DomainEvent(val aggregateId: Long)

class TaskAssigned(
    aggregateId: Long,
    assigneePublicId: UUID
) : DomainEvent(aggregateId)

class TaskCompleted(
    aggregateId: Long
) : DomainEvent(aggregateId)
