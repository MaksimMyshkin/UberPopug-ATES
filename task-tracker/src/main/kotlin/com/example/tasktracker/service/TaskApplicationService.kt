package com.example.tasktracker.service

import com.example.tasktracker.domain.Task
import com.example.tasktracker.repository.TaskRepository
import com.example.tasktracker.security.CurrentUserContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

// TODO На всех операциях добавить отправку сгенерированных Task событий в kafka
@Service
class TaskApplicationService(
    private val taskRepository: TaskRepository,
    private val employeeDomainService: EmployeeDomainService,
    private val currentUserContext: CurrentUserContext
) {

    fun findAssigned(): Iterable<Task> {
        return taskRepository.findByAssigneePublicId(currentUserContext.publicId)
    }

    fun create(title: String, description: String): Long {
        val task = Task(title, description, employeeDomainService.randomWorkerPublicId())
        return taskRepository.save(task).id!!
    }

    fun reassignAllTasks() {
        check(currentUserContext.hasAnyRole("admin", "manager"))
        for (task in taskRepository.findAll()) {
            task.reassign(employeeDomainService.randomWorkerPublicId())
        }
    }

    fun complete(taskId: Long) {
        val task = taskRepository.findByIdOrNull(taskId)
            ?: throw IllegalArgumentException("Задача c id=$taskId не найдена")
        check(task.assigneePublicId == currentUserContext.publicId)

        task.complete()
        taskRepository.save(task)
    }
}
