package com.example.tasktracker.domain

import com.example.tasktracker.domain.event.DomainEvent
import com.example.tasktracker.domain.event.TaskCompleted
import com.example.tasktracker.domain.event.TaskAssigned
import com.example.tasktracker.domain.event.TaskCreated
import java.util.UUID
import javax.persistence.*

/**
 * @param assigneePublicId идентификатор сотрудника из Auth сервиса
 */
@Entity
class Task private constructor(
    val title: String,
    val description: String,
    assigneePublicId: UUID
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val publicId: UUID = UUID.randomUUID()

    var assigneePublicId: UUID = assigneePublicId
        private set

    var status: Status = Status.IN_PROGRESS
        private set

    fun assign(employeePublicId: UUID): DomainEvent {
        assigneePublicId = employeePublicId
        return TaskAssigned(publicId, assigneePublicId)
    }

    fun complete(): DomainEvent {
        check(status == Status.IN_PROGRESS)
        status = Status.COMPLETED
        return TaskCompleted(publicId)
    }

    enum class Status {
        IN_PROGRESS,
        COMPLETED
    }

    companion object {

        fun create(title: String, description: String, assigneePublicId: UUID): Pair<Task, DomainEvent> {
            val task = Task(title, description, assigneePublicId)
            val event = TaskCreated(task.publicId, title, description, assigneePublicId)
            return Pair(task, event)
        }
    }
}
