package com.example.accounting.consumer

import com.example.accounting.consumer.event.TaskAssigned
import com.example.accounting.consumer.event.TaskCompleted
import com.example.accounting.consumer.event.TaskCreated
import com.example.accounting.service.AccountingApplicationService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.reflect.typeOf

@Component
class TaskConsumer(private val accountService: AccountingApplicationService) {

    @KafkaListener(topics = ["tasks"])
    fun consume(record: ConsumerRecord<UUID, Any>) {
        val event = record.value()
        when (event) {
            is TaskCreated -> accountService.onTaskCreation(event.publicId, event.assigneePublicId)
            is TaskAssigned -> accountService.onTaskAssignment(event.assigneePublicId, event.publicId)
            is TaskCompleted -> accountService.onTaskCompletion(event.publicId)
            else -> throw IllegalArgumentException("Неизвестный тип события ${event.javaClass}")
        }
    }
}
