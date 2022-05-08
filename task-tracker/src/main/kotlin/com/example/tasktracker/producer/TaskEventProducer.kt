package com.example.tasktracker.producer

import com.example.tasktracker.domain.event.CudEvent
import com.example.tasktracker.domain.event.DomainEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class TaskEventProducer(private val kafkaTemplate: KafkaTemplate<Long, Any>) {

    fun send(event: CudEvent) {
        kafkaTemplate.send("tasks-stream", event.aggregateId, event)
    }

    fun send(event: DomainEvent) {
        kafkaTemplate.send("tasks", event.aggregateId, event)
    }
}
