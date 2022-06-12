package com.funy4.domain.usecase.transactions

import com.funy4.domain.repo.ExpensesRepo
import com.funy4.domain.repo.TransactionRepo
import javax.inject.Inject

class GetAllExpensesTransactionsUseCase @Inject constructor(
    private val expensesRepo: ExpensesRepo
){
    operator fun invoke() = expensesRepo.getAllExpensesWithTransactionsFlow()
}