package com.example.tasktracker.producer

import com.example.tasktracker.domain.event.DomainEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TaskEventProducer(private val kafkaTemplate: KafkaTemplate<UUID, Any>) {

    fun send(event: DomainEvent) {
        kafkaTemplate.send("tasks", event.publicId, event).get()
    }
}
