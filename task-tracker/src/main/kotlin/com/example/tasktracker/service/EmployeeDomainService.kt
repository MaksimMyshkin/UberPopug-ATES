package com.example.tasktracker.service

import com.example.tasktracker.repository.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeDomainService(private val employeeRepository: EmployeeRepository) {

    fun randomWorkerPublicId(): UUID {
        TODO()
    }
}
