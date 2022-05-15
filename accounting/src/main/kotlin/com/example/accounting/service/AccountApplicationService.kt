package com.example.accounting.service

import com.example.accounting.repository.AccountRepository
import com.example.accounting.repository.TaskRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AccountApplicationService(
    private val accountRepository: AccountRepository,
    private val taskRepository: TaskRepository
) {

    fun debitMoneyOnAssignment(ownerPublicId: UUID, taskPublicId: UUID) {
        val task = taskRepository.findByPublicId(taskPublicId)
        val account = accountRepository.findByOwnerPublicId(ownerPublicId)
        account.debitMoney(task.debitedOnAssignmentAmount,
            "Списаны деньги со счета владельца $ownerPublicId за назначение задачи $taskPublicId")
        accountRepository.save(account)
    }

    fun creditMoneyOnCompletion(ownerPublicId: UUID, taskPublicId: UUID) {
        val task = taskRepository.findByPublicId(taskPublicId)
        val account = accountRepository.findByOwnerPublicId(ownerPublicId)
        account.creditMoney(task.creditedOnCompletionAmount,
            "Начислены деньги на счет владельца $ownerPublicId за выполнение задачи $taskPublicId")
        accountRepository.save(account)
    }

    @Scheduled(cron = "0 0 0 0 0 0")
    fun payEarningsOnEndOfDay() {
        for (account in accountRepository.findAll()) {
            account.payEarnings()
            // Отправить уведомление на email account.ownerEmail
        }
    }
}
