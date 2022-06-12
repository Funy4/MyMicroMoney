package com.funy4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cash")
data class CashEntity(
    @PrimaryKey val id: UUID,
    val name: String,
    val icon: Int,
    val money: Double,
)
