package com.example.accounting.domain

import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
class Account(
    @Column(unique = true)
    val ownerPublicId: UUID,

    // TODO Заполнять из событий Auth сервиса
    val ownerEmail: String = "todo@example.com"
) {
    @Transient
    private val logger = LoggerFactory.getLogger(javaClass)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var balance: BigDecimal = BigDecimal.ZERO
        private set

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val transactions: MutableList<Transaction> = mutableListOf()

    fun debitMoney(amount: BigDecimal, description: String) {
        balance -= amount
        transactions.add(Transaction(-amount, description))
    }

    fun creditMoney(amount: BigDecimal, description: String) {
        balance += amount
        transactions.add(Transaction(amount, description))
    }

    fun payEarnings(): BigDecimal {
        if (balance <= BigDecimal.ZERO) {
            logger.info("Аккаунт владельца $ownerPublicId имеет отрицательный или нулевой баланс $balance. " +
                    "Выплата не производится.")
            return BigDecimal.ZERO
        }
        logger.info("Владельцу аккаунта $ownerPublicId выплачено $balance")
        val paidAmount = balance
        balance = BigDecimal.ZERO
        return paidAmount
    }
}
