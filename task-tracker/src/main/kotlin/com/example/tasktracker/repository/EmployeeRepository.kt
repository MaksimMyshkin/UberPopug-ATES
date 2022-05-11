package com.example.tasktracker.repository

import com.example.tasktracker.domain.Employee
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface EmployeeRepository : CrudRepository<Employee, Long> {

    fun findByPublicId(publicId: UUID): Employee

    @Query(
        "select cast(public_id as varchar) from employee where role = 'worker' order by random() limit 1",
        nativeQuery = true
    )
    fun findRandomWorkerPublicId(): String

    fun deleteByPublicId(publicId: UUID)
}
