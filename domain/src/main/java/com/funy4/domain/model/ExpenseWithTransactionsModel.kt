package com.funy4.domain.model

data class ExpenseWithTransactionsModel(
    val expenseModel: ExpenseModel,
    val transactions: List<TransactionModel>
)
