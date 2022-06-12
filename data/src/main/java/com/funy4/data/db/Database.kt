package com.funy4.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.funy4.data.db.dao.CashDao
import com.funy4.data.db.dao.ExpensesDao
import com.funy4.data.db.dao.IncomeDao
import com.funy4.data.db.dao.TransactionDao
import com.funy4.data.db.entity.*
import com.funy4.data.model.TransactionsWithExpensesEntityModel
import com.funy4.data.model.TransactionsWithIncomeEntityModel

@Database(
    entities = [
        CashEntity::class,
        TransactionEntity::class,
        ExpensesEntity::class,
        ExpenseTransactionEntity::class,
        IncomeEntity::class,
        IncomeTransactionEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun cashDao(): CashDao
    abstract fun transactionDao(): TransactionDao
    abstract fun incomeDao(): IncomeDao
    abstract fun expensesDao(): ExpensesDao
}