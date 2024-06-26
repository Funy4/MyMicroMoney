package com.funy4.domain.usecase.expenses

import com.funy4.domain.repo.ExpensesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


class DeleteExpensesUseCase @Inject constructor(
    private val expensesRepo: ExpensesRepo
) {
    suspend operator fun invoke(id: UUID) = withContext(Dispatchers.IO) {
        expensesRepo.delete(id)
    }
}