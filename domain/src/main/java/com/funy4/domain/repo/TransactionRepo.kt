package com.funy4.domain.repo

import com.funy4.domain.model.CashModel
import com.funy4.domain.model.TransactionModel
import com.funy4.domain.model.TransactionShortModel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface TransactionRepo {
    suspend fun create(model: TransactionModel)

    suspend fun update(model: TransactionModel)

    suspend fun delete(id: UUID)

    suspend fun get(id: UUID): TransactionModel?

    fun getAllFlow(): Flow<List<TransactionModel>>

    fun getAllShort(): List<TransactionShortModel>
}