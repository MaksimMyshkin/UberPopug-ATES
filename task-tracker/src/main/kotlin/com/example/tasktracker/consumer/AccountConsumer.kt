package com.example.tasktracker.consumer

import com.example.tasktracker.consumer.event.AccountEvent
import com.example.tasktracker.domain.Employee
import com.example.tasktracker.repository.EmployeeRepository
import com.example.tasktracker.service.EmployeeApplicationService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class AccountConsumer(private val employeeApplicationService: EmployeeApplicationService) {

    @KafkaListener(topics = ["accounts-stream"])
    fun consume(event: AccountEvent) {
        when (event.eventName) {
            "AccountCreated" -> employeeApplicationService.create(event.data.publicId, event.data.fullName)
            "AccountUpdated" -> employeeApplicationService.updateName(event.data.publicId, event.data.fullName)
            "AccountDeleted" -> employeeApplicationService.delete(event.data.publicId)
            // TODO Скорее всего нужно также сохранять роли
            else -> throw IllegalArgumentException("Неизвестное событие: ${event.eventName}")
        }
    }
}
