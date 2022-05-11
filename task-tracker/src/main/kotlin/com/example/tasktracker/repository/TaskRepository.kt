package com.example.tasktracker.repository

import com.example.tasktracker.domain.Task
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface TaskRepository : CrudRepository<Task, Long> {

    fun findByAssigneePublicId(assigneePublicId: UUID): Iterable<Task>
}
