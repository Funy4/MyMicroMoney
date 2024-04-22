package com.funy4.domain.usecase.predict

import com.funy4.domain.model.TransactionShortModel
import com.funy4.domain.repo.ApiRepo
import com.funy4.domain.repo.TransactionRepo
import javax.inject.Inject

class PredictDataUseCase @Inject constructor(
    private val apiRepo: ApiRepo,
    private val transactionRepo: TransactionRepo
) {
    suspend operator fun invoke(days: Int): List<TransactionShortModel> =
        apiRepo.predict(transactionRepo.getAllShort(), days)
}