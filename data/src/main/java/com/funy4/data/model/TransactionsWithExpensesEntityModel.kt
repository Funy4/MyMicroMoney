package com.funy4.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.funy4.data.db.entity.ExpenseTransactionEntity
import com.funy4.data.db.entity.ExpensesEntity
import com.funy4.data.db.entity.TransactionEntity

data class TransactionsWithExpensesEntityModel(
    @Embedded val transactions: TransactionEntity,
    @Relation(
        parentColumn = "id",
        entity = ExpensesEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ExpenseTransactionEntity::class,
            parentColumn = "transactionId",
            entityColumn = "expenseId"
        )
    )
    val expenses: List<ExpensesEntity>
)