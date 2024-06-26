package com.funy4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "incomes")
data class IncomeEntity(
    @PrimaryKey val id: UUID,
    val name: String,
    val color: Int,
    val icon: Int,
)
