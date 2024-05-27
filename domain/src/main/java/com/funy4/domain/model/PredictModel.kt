package com.funy4.domain.model

data class PredictModel (
    val predictedTransactions: List<TransactionShortModel>,
    val isPositive: Boolean
)