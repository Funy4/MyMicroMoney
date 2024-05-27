package com.funy4.domain.usecase.expenses

import com.funy4.domain.repo.ExpensesRepo
import com.funy4.domain.repo.TransactionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val expensesRepo: ExpensesRepo,
    private val transactionRepo: TransactionRepo
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
//        launch {
//            transactionRepo.getAllFlow().collect{
//                println("KEK trans: $it")
//            }
//        }
        expensesRepo.getAllFlow()
    }
}