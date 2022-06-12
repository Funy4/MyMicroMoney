package com.funy4.domain.model

data class IncomeWithTransactionModel(
    val income: IncomeModel,
    val transactions: List<TransactionModel>
)
