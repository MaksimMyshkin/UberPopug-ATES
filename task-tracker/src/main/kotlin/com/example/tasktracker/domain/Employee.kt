package com.example.tasktracker.domain

import java.util.*
import javax.persistence.*

@Entity
class Employee(
    @Column(unique = true)
    val publicId: UUID,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var role: String = "worker"
        private set

    fun changeRole(role: String) {
        this.role = role
    }
}
