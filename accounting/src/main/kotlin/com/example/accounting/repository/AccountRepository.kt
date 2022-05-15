package com.example.accounting.repository

import com.example.accounting.domain.Account
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface AccountRepository : CrudRepository<Account, Long> {

    fun findByOwnerPublicId(ownerPublicId: UUID): Account
}
