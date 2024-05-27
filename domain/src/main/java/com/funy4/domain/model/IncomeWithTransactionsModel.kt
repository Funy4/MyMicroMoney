package com.funy4.domain.model

data class IncomeWithTransactionsModel(
    val income: IncomeModel,
    val transactions: List<TransactionModel>
)
