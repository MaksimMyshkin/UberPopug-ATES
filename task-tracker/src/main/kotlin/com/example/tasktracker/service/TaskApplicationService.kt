package com.example.tasktracker.service

import com.example.tasktracker.domain.Task
import com.example.tasktracker.producer.TaskEventProducer
import com.example.tasktracker.repository.TaskRepository
import com.example.tasktracker.security.CurrentUserContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TaskApplicationService(
    private val taskRepository: TaskRepository,
    private val employeeDomainService: EmployeeDomainService,
    private val currentUserContext: CurrentUserContext,
    private val taskEventProducer: TaskEventProducer
) {

    fun findAssigned(): Iterable<Task> {
        return taskRepository.findByAssigneePublicId(currentUserContext.publicId)
    }

    fun create(title: String, description: String): Long {
        var (task, event) = Task.create(title, description, employeeDomainService.findRandomWorkerPublicId())
        task = taskRepository.save(task)
        taskEventProducer.send(event)
        return task.id!!
    }

    fun reassignAllTasks() {
        check(currentUserContext.hasAnyRole("admin", "manager"))
        for (task in taskRepository.findAll()) {
            val event = task.assign(employeeDomainService.findRandomWorkerPublicId())
            taskRepository.save(task)
            taskEventProducer.send(event)
        }
    }

    fun complete(taskId: Long) {
        val task = taskRepository.findByIdOrNull(taskId)
            ?: throw IllegalArgumentException("Задача c id=$taskId не найдена")
        check(task.assigneePublicId == currentUserContext.publicId)

        val event = task.complete()
        taskRepository.save(task)
        taskEventProducer.send(event)
    }
}
