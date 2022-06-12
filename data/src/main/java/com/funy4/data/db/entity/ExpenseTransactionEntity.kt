package com.funy4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expenses_transactions", primaryKeys = ["expenseId", "transactionId"])
data class ExpenseTransactionEntity(
    val expenseId: UUID,
    val transactionId: UUID
)
