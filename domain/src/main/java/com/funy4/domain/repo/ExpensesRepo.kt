package com.funy4.domain.repo

import com.funy4.domain.model.ExpenseModel
import com.funy4.domain.model.ExpenseWithTransactionsModel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ExpensesRepo {
    suspend fun create(model: ExpenseModel)

    suspend fun update(model: ExpenseModel)

    suspend fun delete(id: UUID)

    suspend fun get(id: UUID): ExpenseModel?

    fun getAllFlow(): Flow<List<ExpenseModel>>

//    fun getAllExpensesWithTransactionsFlow(): Flow<List<ExpenseWithTransactionsModel>>
}