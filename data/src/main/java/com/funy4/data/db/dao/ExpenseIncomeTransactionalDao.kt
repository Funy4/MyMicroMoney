package com.funy4.data.db.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.funy4.data.db.entity.ExpenseTransactionEntity
import com.funy4.data.db.entity.IncomeTransactionEntity
import com.funy4.data.db.entity.TransactionEntity
import java.lang.RuntimeException
import java.util.UUID

@Dao
interface ExpenseIncomeTransactionalDao {
    @Transaction
    suspend fun insertExpenseTransaction(
        transactionDao: TransactionDao,
        cashDao: CashDao,
        expenseId: UUID,
        transaction: TransactionEntity
    ) {
        val cash = cashDao.get(transaction.cashId) ?: throw RuntimeException("cash not found")
        cashDao.update(cash.copy(money = cash.money - transaction.cost))
        transactionDao.insert(transaction)
        transactionDao.insertExpense(
            ExpenseTransactionEntity(
                expenseId = expenseId,
                transactionId = transaction.id
            )
        )
    }

    @Transaction
    suspend fun insertIncomeTransaction(
        transactionDao: TransactionDao,
        cashDao: CashDao,
        incomeId: UUID,
        transaction: TransactionEntity
    ) {
        val cash = cashDao.get(transaction.cashId) ?: throw RuntimeException("cash not found")
        cashDao.update(cash.copy(money = cash.money + transaction.cost))
        transactionDao.insert(transaction)
        transactionDao.insertIncome(
            IncomeTransactionEntity(
                incomeId = incomeId,
                transactionId = transaction.id
            )
        )
    }
}