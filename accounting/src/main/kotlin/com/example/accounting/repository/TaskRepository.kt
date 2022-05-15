package com.example.accounting.repository

import com.example.accounting.domain.Task
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface TaskRepository : CrudRepository<Task, Long> {

    fun findByPublicId(publicId: UUID): Task
}
