package com.example.accounting.controller

import com.example.accounting.controller.dto.CountWithNegativeBalanceDto
import com.example.accounting.controller.dto.EarnedByManagementDto
import com.example.accounting.controller.dto.TaskViewDto
import com.example.accounting.service.AnalyticApplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts/analytic")
class AccountAnalyticController(private val service: AnalyticApplicationService) {

    @GetMapping("/earned-by-management-today")
    fun findEarnedByManagementToday(): EarnedByManagementDto {
        val amount = service.findEarnedByManagementToday()
        return EarnedByManagementDto(amount)
    }

    @GetMapping("/count-with-negative-balance")
    fun findCountWithNegativeBalance(): CountWithNegativeBalanceDto {
        val count = service.findCountWithNegativeBalance()
        return CountWithNegativeBalanceDto(count)
    }

    @GetMapping("/most-expensive-today-task")
    fun findMostExpensiveTodayTask(): TaskViewDto {
        val task = service.findMostExpensiveTodayTask()
        return TaskViewDto(task.id!!, task.publicId, task.debitedOnAssignmentAmount, task.creditedOnCompletionAmount)
    }
}
