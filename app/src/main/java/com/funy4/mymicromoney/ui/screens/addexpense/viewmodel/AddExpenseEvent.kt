package com.funy4.mymicromoney.ui.screens.addexpense.viewmodel

import com.funy4.domain.model.CashModel
import com.funy4.domain.model.ExpenseModel
import java.time.LocalDate

sealed interface AddExpenseEvent {
    data class OnTransactionNameChange(val transactionName: String): AddExpenseEvent
    data class OnMoneyChange(val money: String): AddExpenseEvent
    data class OnExpenseChange(val expense: ExpenseModel): AddExpenseEvent
    data class OnCashChange(val cashModel: CashModel): AddExpenseEvent
    data class OnDateChange(val date: LocalDate): AddExpenseEvent
    object SaveExpense: AddExpenseEvent
    object OnBack: AddExpenseEvent
}