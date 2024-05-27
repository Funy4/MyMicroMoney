package com.funy4.domain.usecase.transactions

import com.funy4.domain.model.TransactionModel
import com.funy4.domain.model.type.TransactionType
import com.funy4.domain.repo.TransactionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

class AddTransactionIncomeUseCase @Inject constructor(
    private val transactionRepo: TransactionRepo
) {
    suspend operator fun invoke(
        name: String,
        cost: Double,
        cashId: UUID,
        incomeId: UUID,
        date: LocalDate
    ) = withContext(Dispatchers.IO) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        transactionRepo.createIncomeTransaction(
            TransactionModel(
                id = UUID.randomUUID(),
                name = name,
                cost = cost,
                cashId = cashId,
                transactionType = TransactionType.INCOME,
                date = date.format(formatter)
            ),
            incomeId
        )
    }
}