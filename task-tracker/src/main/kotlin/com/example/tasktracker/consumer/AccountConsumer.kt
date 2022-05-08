package com.example.tasktracker.consumer

import com.example.tasktracker.consumer.event.AccountEvent
import com.example.tasktracker.service.EmployeeApplicationService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class AccountConsumer(private val employeeApplicationService: EmployeeApplicationService) {

    @KafkaListener(topics = ["accounts-stream", "accounts"])
    fun consume(event: AccountEvent) {
        when (event.eventName) {
            "AccountCreated" -> employeeApplicationService.create(event.data.publicId)
            "AccountUpdated" -> return // Неинтересно изменение ФИО
            "AccountDeleted" -> employeeApplicationService.delete(event.data.publicId)
            "AccountRoleChanged" -> employeeApplicationService.changeRole(event.data.publicId, event.data.role!!)
            else -> throw IllegalArgumentException("Неизвестное событие: ${event.eventName}")
        }
    }
}
