package com.funy4.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.funy4.data.db.entity.ExpenseTransactionEntity
import com.funy4.data.db.entity.IncomeEntity
import com.funy4.data.db.entity.IncomeTransactionEntity
import com.funy4.data.db.entity.TransactionEntity

data class IncomeWithTransactionEntityModel(
    @Embedded val income: IncomeEntity,
    @Relation(
        parentColumn = "id",
        entity = TransactionEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = IncomeTransactionEntity::class,
            parentColumn = "incomeId",
            entityColumn = "transactionId"
        )
    )
    val transactions: List<TransactionEntity>
)
