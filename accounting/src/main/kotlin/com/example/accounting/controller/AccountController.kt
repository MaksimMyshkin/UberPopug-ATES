package com.example.accounting.controller

import com.example.accounting.controller.dto.AccountViewDto
import com.example.accounting.domain.Account
import com.example.accounting.service.AccountingApplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountingApplicationService: AccountingApplicationService) {

    @GetMapping
    fun findAll(): List<AccountViewDto> {
        return accountingApplicationService.findAll()
            .map { it.toViewDto() }
    }

    @GetMapping("/current-user")
    fun findByCurrentUser(): AccountViewDto {
        return accountingApplicationService.findByCurrentUser()
            .toViewDto()
    }

    private fun Account.toViewDto() = AccountViewDto(id!!, balance, ownerPublicId)
}
