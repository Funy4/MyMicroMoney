package com.funy4.domain.usecase.expenses

import com.funy4.domain.repo.ExpensesRepo
import java.util.*
import javax.inject.Inject

class GetExpenseUseCase @Inject constructor(
    private val expensesRepo: ExpensesRepo
) {
    suspend operator fun invoke(id: UUID) {
        expensesRepo.get(id)
    }
}