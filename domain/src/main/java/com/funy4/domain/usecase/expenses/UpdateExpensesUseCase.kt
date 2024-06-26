package com.funy4.domain.usecase.expenses

import com.funy4.domain.model.ExpenseModel
import com.funy4.domain.repo.ExpensesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UpdateExpensesUseCase @Inject constructor(
    private val expensesRepo: ExpensesRepo
) {
    suspend operator fun invoke(model: ExpenseModel) = withContext(Dispatchers.IO) {
        expensesRepo.update(model)
    }
}