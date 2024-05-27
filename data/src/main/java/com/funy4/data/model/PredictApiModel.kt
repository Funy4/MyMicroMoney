package com.funy4.data.model

import java.time.LocalDateTime

data class PredictApiModel(val transactions: List<Transaction>, val days: Int) {
    data class Transaction(val date: LocalDateTime, val amount: Double)
}