package com.funy4.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.funy4.data.db.entity.ExpenseTransactionEntity
import com.funy4.data.db.entity.ExpensesEntity
import com.funy4.data.db.entity.TransactionEntity

data class ExpenseWithTransactionsEntityModel(
    @Embedded val expense: ExpensesEntity,
    @Relation(
        parentColumn = "id",
        entity = TransactionEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ExpenseTransactionEntity::class,
            parentColumn = "expenseId",
            entityColumn = "transactionId"
        )
    )
    val transactions: List<TransactionEntity>
)
