package com.funy4.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.funy4.data.db.entity.ExpenseTransactionEntity
import com.funy4.data.db.entity.IncomeEntity
import com.funy4.data.db.entity.IncomeTransactionEntity
import com.funy4.data.db.entity.TransactionEntity

data class TransactionsWithIncomeEntityModel(
    @Embedded val transactions: TransactionEntity,
    @Relation(
        parentColumn = "id",
        entity = IncomeEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            IncomeTransactionEntity::class,
            parentColumn = "transactionId",
            entityColumn = "incomeId"
        )
    )
    val incomes: List<IncomeEntity>
)