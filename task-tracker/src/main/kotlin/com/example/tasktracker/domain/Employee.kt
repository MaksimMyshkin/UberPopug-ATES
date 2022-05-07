package com.example.tasktracker.domain

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Employee(
    // TODO Индекс по publicId
    val publicId: UUID,
    fullName: String?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var fullName: String? = fullName
        private set

    fun updateName(fullName: String?) {
        this.fullName = fullName
    }
}
