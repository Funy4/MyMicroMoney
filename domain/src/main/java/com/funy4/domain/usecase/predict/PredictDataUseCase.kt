package com.funy4.domain.usecase.predict

import com.funy4.domain.model.PredictModel
import com.funy4.domain.model.TransactionShortModel
import com.funy4.domain.repo.ApiRepo
import com.funy4.domain.repo.TransactionRepo
import javax.inject.Inject

class PredictDataUseCase @Inject constructor(
    private val apiRepo: ApiRepo,
    private val transactionRepo: TransactionRepo
) {
    suspend operator fun invoke(days: Int): PredictModel {
        val pastTransactions = transactionRepo.getAllShort()
        val predict = apiRepo.predict(pastTransactions, days)
        return PredictModel(
            predictedTransactions = predict,
            isPositive = pastTransactions.sumOf { it.amount } < predict.sumOf { it.amount })
    }
}