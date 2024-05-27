package com.funy4.domain.usecase.transactions

import com.funy4.domain.model.TransactionModel
import com.funy4.domain.model.type.TransactionType
import com.funy4.domain.repo.TransactionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepo: TransactionRepo
) {
    suspend operator fun invoke(
        name: String,
        cost: Double,
        cashId: UUID,
        transactionType: TransactionType,
        expenseOrIncomeId: UUID,
        date: LocalDate
    ) = withContext(Dispatchers.IO) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        transactionRepo.create(
            TransactionModel(
                id = UUID.randomUUID(),
                name = name,
                cost = cost,
                cashId = cashId,
                transactionType = transactionType,
                date = date.format(formatter)
            )
        )
    }
}