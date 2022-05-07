package com.example.tasktracker.controller

import com.example.tasktracker.controller.dto.TaskCreationDto
import com.example.tasktracker.controller.dto.TaskViewDto
import com.example.tasktracker.domain.Task
import com.example.tasktracker.service.TaskApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/tasks")
class TaskController(private val taskApplicationService: TaskApplicationService) {

    @GetMapping
    fun findAll(): List<TaskViewDto> {
        return taskApplicationService.findAssigned()
            .map { it.toViewDto() }
    }

    @PostMapping
    fun create(dto: TaskCreationDto): ResponseEntity<Void> {
        val taskId = taskApplicationService.create(dto.title, dto.description)
        return ResponseEntity.created(URI("/tasks/$taskId")).build()
    }

    @PostMapping("/reassign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun reassignAllTasks() {
        taskApplicationService.reassignAllTasks()
    }

    @PostMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun complete(id: Long) {
        taskApplicationService.complete(id)
    }

    private fun Task.toViewDto(): TaskViewDto = TaskViewDto(id!!, title, description, assigneePublicId, status)
}
