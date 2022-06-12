package com.funy4.domain.model

import java.util.*

data class IncomeModel(
    val id: UUID,
    val name: String,
    val color: Int,
    val iconId: Int,
    val money: Double,
)
