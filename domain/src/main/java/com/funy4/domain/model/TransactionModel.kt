package com.funy4.domain.model

import com.funy4.domain.model.type.TransactionType
import java.util.*

data class TransactionModel(
    val id: UUID,
    val name: String,
    val cost: Double,
    val cashId: UUID,
    val transactionType: TransactionType,
    val date: String
)
