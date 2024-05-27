package com.funy4.domain.usecase.transactions

import com.funy4.domain.repo.TransactionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllExpensesTransactions @Inject constructor(
    private val transactionRepo: TransactionRepo
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        transactionRepo.getAllFlow()
    }
}