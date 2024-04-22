package com.funy4.data.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.funy4.domain.model.TransactionShortModel
import com.funy4.domain.repo.ApiRepo
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class ApiRepoImpl @Inject constructor(

) : ApiRepo {
    override suspend fun predict(
        transactions: List<TransactionShortModel>,
        daysToPredict: Int
    ): List<TransactionShortModel> {
        delay(500)
        return transactions + buildList {
            repeat(daysToPredict) {
                TransactionShortModel(
                    transactions.last().date.plusDays(it + 1L),
                    Random.nextDouble()
                )
            }
        }
    }
}