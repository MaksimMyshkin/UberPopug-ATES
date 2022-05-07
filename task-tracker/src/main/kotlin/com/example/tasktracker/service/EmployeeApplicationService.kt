package com.example.tasktracker.service

import com.example.tasktracker.domain.Employee
import com.example.tasktracker.repository.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeApplicationService(private val employeeRepository: EmployeeRepository) {

    fun create(publicId: UUID, fullName: String?) {
        employeeRepository.save(Employee(publicId, fullName))
    }

    fun updateName(publicId: UUID, fullName: String?) {
        val employee = employeeRepository.findByPublicId(publicId)
        employee.updateName(fullName)
        employeeRepository.save(employee)
    }

    fun delete(publicId: UUID) {
        employeeRepository.deleteByPublicId(publicId)
    }
}
