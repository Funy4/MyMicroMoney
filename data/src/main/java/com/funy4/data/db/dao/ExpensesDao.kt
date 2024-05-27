package com.funy4.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funy4.data.db.entity.ExpensesEntity
import com.funy4.data.model.ExpenseWithTransactionsEntityModel
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ExpensesDao : BaseDao<ExpensesEntity> {
    @Query("select * from expenses where id = :id")
    suspend fun getWithTransaction(id: UUID): ExpenseWithTransactionsEntityModel?

    @Query("select * from expenses")
    fun getAll(): Flow<List<ExpensesEntity>>

    @Transaction
    @Query("select * from expenses")
    fun getAllWithTransactions(): Flow<List<ExpenseWithTransactionsEntityModel>>
}