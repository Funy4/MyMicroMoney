package com.funy4.domain.repo

import com.funy4.domain.model.CashModel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface CashRepo {
    suspend fun create(model: CashModel)

    suspend fun update(model: CashModel)

    suspend fun delete(id: UUID)

    suspend fun get(id: UUID): CashModel?

    suspend fun getCashSum(): Flow<Double?>

    fun getAllFlow(): Flow<List<CashModel>>
}