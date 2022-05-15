package com.example.accounting.service

import com.example.accounting.client.EmailClient
import com.example.accounting.domain.Account
import com.example.accounting.repository.AccountRepository
import com.example.accounting.repository.TaskRepository
import com.example.accounting.security.CurrentUserContext
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class AccountApplicationService(
    private val accountRepository: AccountRepository,
    private val taskRepository: TaskRepository,
    private val emailClient: EmailClient,
    private val currentUserContext: CurrentUserContext
) {

    fun findAll(): Iterable<Account> {
        // TODO Кажется в Auth сервисе должна быть роль accountant вместо manager
        check(currentUserContext.hasAnyRole("admin", "manager"))
        return accountRepository.findAll()
    }

    fun findByCurrentUser(): Account {
        return accountRepository.findByOwnerPublicId(currentUserContext.publicId)
    }

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
            val paidAmount = account.payEarnings()
            if (paidAmount > BigDecimal.ZERO) {
                emailClient.send(account.ownerEmail, "Вам выплачено: $paidAmount")
            }
        }
    }
}
