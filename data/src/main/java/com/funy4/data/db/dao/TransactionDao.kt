package com.funy4.data.db.dao

import androidx.room.*
import com.funy4.data.db.entity.ExpenseTransactionEntity
import com.funy4.data.db.entity.IncomeTransactionEntity
import com.funy4.data.db.entity.TransactionEntity
import com.funy4.data.model.ExpenseWithTransactionsEntityModel
import com.funy4.data.model.IncomeWithTransactionsEntityModel
import com.funy4.data.model.TransactionsWithExpensesEntityModel
import com.funy4.data.model.TransactionsWithIncomeEntityModel
import com.funy4.domain.model.type.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TransactionDao : BaseDao<TransactionEntity> {
    @Insert
    suspend fun insertExpense(model: ExpenseTransactionEntity)

    @Insert
    suspend fun insertIncome(model: IncomeTransactionEntity)


    @Query("select * from 'transaction' where id = :id")
    suspend fun get(id: UUID): TransactionEntity?

    @Query("select * from expenses_transactions inner join `transaction` where expenseId = :expenseId")
    suspend fun getByExpenseId(expenseId: UUID): List<TransactionEntity>

    @Query("select * from incomes_transactions inner join `transaction` where incomeId = :incomeId")
    suspend fun getByIncomeId(incomeId: UUID): List<TransactionEntity>

    @Query("select * from `transaction` where transactionType = :type")
    suspend fun getCurrentTypeTransactions(type: TransactionType): List<TransactionEntity>

    @Query("select * from 'transaction'")
    fun getAll(): Flow<List<TransactionEntity>>

    @Query("select * from `expenses`")
    fun getAllExpenses(): Flow<List<ExpenseWithTransactionsEntityModel>>

    @Query("select * from `incomes`")
    fun getAllIncome(): Flow<List<IncomeWithTransactionsEntityModel>>

    @Transaction
    @Query("select * from `transaction`")
    fun getTransactionsWithExpenses(): Flow<List<TransactionsWithExpensesEntityModel>>

    @Transaction
    @Query("select * from `transaction`")
    fun getTransactionsWithIncomes(): Flow<List<TransactionsWithIncomeEntityModel>>
}