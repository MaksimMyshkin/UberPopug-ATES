package com.example.accounting.controller.dto

import java.math.BigDecimal
import java.util.*

data class AccountViewDto(
    val id: Long,
    var balance: BigDecimal,
    // TODO Сохранять в БД ФИО, чтобы выводить тут вместо id?
    val ownerPublicId: UUID,
)
