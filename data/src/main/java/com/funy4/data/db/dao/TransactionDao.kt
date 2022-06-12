package com.funy4.data.db.dao

import androidx.room.*
import com.funy4.data.db.entity.TransactionEntity
import com.funy4.data.model.TransactionsWithExpensesEntityModel
import com.funy4.data.model.TransactionsWithIncomeEntityModel
import com.funy4.domain.model.type.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TransactionDao : BaseDao<TransactionEntity> {
    @Query("select * from 'transaction' where id = :id")
    suspend fun get(id: UUID): TransactionEntity?

    @Query("select * from `transaction` where transactionType = :type")
    suspend fun getCurrentTypeTransactions(type: TransactionType): List<TransactionEntity>

    @Query("select * from 'transaction'")
    fun getAll(): Flow<List<TransactionEntity>>

    @Transaction
    @Query("select * from `transaction`")
    fun getTransactionsWithExpenses(): Flow<List<TransactionsWithExpensesEntityModel>>

    @Transaction
    @Query("select * from `transaction`")
    fun getTransactionsWithIncomes(): Flow<List<TransactionsWithIncomeEntityModel>>
}