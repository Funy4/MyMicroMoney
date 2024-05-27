package com.funy4.mymicromoney.ui.screens.addexpense.viewmodel

import com.funy4.domain.model.CashModel
import com.funy4.domain.model.ExpenseModel
import com.funy4.mymicromoney.ui.screens.addincome.viewmodel.AddIncomeEvent
import java.time.LocalDate
import java.util.UUID

sealed interface AddExpenseEvent {
    data class InitBaseExpense(val id: UUID): AddExpenseEvent

    data class OnTransactionNameChange(val transactionName: String) : AddExpenseEvent
    data class OnMoneyChange(val money: String) : AddExpenseEvent
    object OnChangeExpenseClick : AddExpenseEvent
    object OnChangeCashClick : AddExpenseEvent
    object OnDismissChangeExpense : AddExpenseEvent
    object OnDismissChangeCash : AddExpenseEvent
    data class OnExpenseChange(val expense: ExpenseModel) : AddExpenseEvent
    data class OnCashChange(val cashModel: CashModel) : AddExpenseEvent

    data class OnDateChange(val date: LocalDate) : AddExpenseEvent
    object OnSelectDateClick : AddExpenseEvent
    object OnDismissSelectDate : AddExpenseEvent

    data class OnSaveExpenseClick(val name: String, val cost: Double) : AddExpenseEvent
    object OnBack : AddExpenseEvent
}