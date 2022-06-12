package com.funy4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.funy4.domain.model.type.TransactionType
import java.util.*

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey val id: UUID,
    val name: String,
    val cost: Double,
    val cashId: UUID,
    val transactionType: TransactionType,
    val date: String
)