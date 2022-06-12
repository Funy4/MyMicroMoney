package com.funy4.domain.usecase.expenses

import com.funy4.domain.repo.ExpensesRepo
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val expensesRepo: ExpensesRepo
) {
    operator fun invoke() = expensesRepo.getAllFlow()
}