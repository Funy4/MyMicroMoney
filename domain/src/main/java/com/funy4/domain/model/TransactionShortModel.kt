package com.funy4.domain.model

import java.time.LocalDateTime

data class TransactionShortModel(
    val date: LocalDateTime,
    val amount: Double
)