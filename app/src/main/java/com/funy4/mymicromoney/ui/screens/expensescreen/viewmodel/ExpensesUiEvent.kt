package com.funy4.mymicromoney.ui.screens.expensescreen.viewmodel

import java.util.*

sealed interface ExpensesUiEvent {
    object OpenAddExpenseScreen: ExpensesUiEvent
    data class OpenAddTransactionScreen(val id: UUID): ExpensesUiEvent
}
