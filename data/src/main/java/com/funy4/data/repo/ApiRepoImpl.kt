package com.funy4.data.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.funy4.data.api.ApiService
import com.funy4.data.model.PredictApiModel
import com.funy4.domain.model.TransactionShortModel
import com.funy4.domain.repo.ApiRepo
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class ApiRepoImpl @Inject constructor(
    private val apiServiceFactory: ApiService.Factory
) : ApiRepo {
    private val apiService = apiServiceFactory.get()
    override suspend fun predict(
        transactions: List<TransactionShortModel>,
        daysToPredict: Int
    ): List<TransactionShortModel> = apiService.predict(
        PredictApiModel(
            transactions = transactions.map { PredictApiModel.Transaction(it.date, it.amount) },
            days = daysToPredict
        )
    ).map { TransactionShortModel(it.date, it.amount) }
}