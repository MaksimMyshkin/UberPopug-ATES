package com.example.accounting.service

import com.example.accounting.domain.Task
import com.example.accounting.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskApplicationService(private val taskRepository: TaskRepository) {

    fun create(publicId: UUID) {
        taskRepository.save(Task(publicId))
    }
}
