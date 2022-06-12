package com.funy4.domain.model

import java.util.*

data class CashModel(
    val id: UUID,
    val name: String,
    val icon: Int,
    val money: Double,
)
