package com.example.accounting.controller

import com.example.accounting.controller.dto.AccountViewDto
import com.example.accounting.domain.Account
import com.example.accounting.service.AccountApplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountApplicationService: AccountApplicationService) {

    @GetMapping
    fun findAll(): List<AccountViewDto> {
        return accountApplicationService.findAll()
            .map { it.toViewDto() }
    }

    @GetMapping("/current-user")
    fun findByCurrentUser(): AccountViewDto {
        return accountApplicationService.findByCurrentUser()
            .toViewDto()
    }

    private fun Account.toViewDto() = AccountViewDto(id!!, balance, ownerPublicId)
}
