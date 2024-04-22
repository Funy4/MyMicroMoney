package com.funy4.domain.repo

import com.funy4.domain.model.TransactionModel
import com.funy4.domain.model.TransactionShortModel

interface ApiRepo {
    suspend fun predict(
        transactions: List<TransactionShortModel>,
        daysToPredict: Int
    ): List<TransactionShortModel>
}