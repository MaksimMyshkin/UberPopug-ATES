package com.example.tasktracker.repository

import com.example.tasktracker.domain.Employee
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface EmployeeRepository : CrudRepository<Employee, Long> {

    fun findByPublicId(publicId: UUID): Employee

    fun deleteByPublicId(publicId: UUID)
}
