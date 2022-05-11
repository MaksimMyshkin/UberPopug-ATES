package com.example.tasktracker.service

import com.example.tasktracker.domain.Employee
import com.example.tasktracker.repository.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeApplicationService(private val employeeRepository: EmployeeRepository) {

    fun create(publicId: UUID) {
        employeeRepository.save(Employee(publicId))
    }

    fun changeRole(publicId: UUID, role: String) {
        val employee = employeeRepository.findByPublicId(publicId)
        employee.changeRole(role)
        employeeRepository.save(employee)
    }

    fun delete(publicId: UUID) {
        employeeRepository.deleteByPublicId(publicId)
    }
}
