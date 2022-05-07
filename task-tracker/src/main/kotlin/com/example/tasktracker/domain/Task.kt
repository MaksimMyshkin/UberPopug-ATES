package com.example.tasktracker.domain

import java.util.UUID
import javax.persistence.*

/**
 * @param assigneePublicId идентификатор сотрудника из Auth сервиса
 */
// TODO Добавить генерацию событий при всех операциях
@Entity
class Task(
    val title: String,
    val description: String,
    assigneePublicId: UUID
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var assigneePublicId: UUID = assigneePublicId
        private set

    var status: Status = Status.IN_PROGRESS
        private set

    fun reassign(employeePublicId: UUID) {
        assigneePublicId = employeePublicId
    }

    fun complete() {
        check(status == Status.IN_PROGRESS)
        status = Status.COMPLETED
    }

    enum class Status {
        IN_PROGRESS,
        COMPLETED
    }
}
