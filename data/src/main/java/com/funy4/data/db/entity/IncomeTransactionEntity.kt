package com.funy4.data.db.entity

import androidx.room.Entity
import java.util.*

@Entity(tableName = "incomes_transactions", primaryKeys = ["incomeId", "transactionId"])
data class IncomeTransactionEntity(
    val incomeId: UUID,
    val transactionId: UUID
)
