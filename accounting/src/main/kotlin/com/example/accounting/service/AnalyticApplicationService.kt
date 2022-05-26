package com.example.accounting.service

import com.example.accounting.domain.Task
import com.example.accounting.repository.AccountRepository
import com.example.accounting.repository.TaskRepository
import com.example.accounting.security.CurrentUserContext
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AnalyticApplicationService(
    private val accountRepository: AccountRepository,
    private val taskRepository: TaskRepository,
    private val currentUserContext: CurrentUserContext
) {

    fun findEarnedByManagementToday(): BigDecimal {
        check(currentUserContext.hasAnyRole("admin", "manager"))
        return accountRepository.findEarnedByManagementToday()
    }

    fun findCountWithNegativeBalance(): Long {
        check(currentUserContext.hasAnyRole("admin"))
        return accountRepository.findCountWithNegativeBalance()
    }

    fun findMostExpensiveTodayTask(): Task {
        check(currentUserContext.hasAnyRole("admin"))
        return taskRepository.findMostExpensiveTodayTask()
    }
}
