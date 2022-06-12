package com.funy4.domain.repo

import com.funy4.domain.model.IncomeModel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IncomeRepo {
    suspend fun create(model: IncomeModel)

    suspend fun update(model: IncomeModel)

    suspend fun delete(id: UUID)

    suspend fun get(id: UUID): IncomeModel?

    fun getAllFlow(): Flow<List<IncomeModel>>
}