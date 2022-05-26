package com.example.accounting.repository

import com.example.accounting.domain.Account
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigDecimal
import java.util.UUID

interface AccountRepository : CrudRepository<Account, Long> {

    fun findByOwnerPublicId(ownerPublicId: UUID): Account

    @Query("from Account where ownerPublicId = :ownerPublicId")
    fun findByOwnerPublicIdOrNull(ownerPublicId: UUID): Account?

    @Query("select sum(amount_change) from transaction where timestamp >= now() - interval '1 day'",
        nativeQuery = true)
    fun findEarnedByManagementToday(): BigDecimal

    @Query("select count(a) from Account a where a.balance < 0")
    fun findCountWithNegativeBalance(): Long
}
