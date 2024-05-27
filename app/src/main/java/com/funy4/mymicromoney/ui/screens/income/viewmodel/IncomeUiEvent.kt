package com.funy4.mymicromoney.ui.screens.income.viewmodel

import com.funy4.mymicromoney.ui.screens.expensescreen.viewmodel.ExpensesUiEvent
import java.util.*

sealed interface IncomeUiEvent {
    data class OpenAddTransactionScreen(val id: UUID): IncomeUiEvent
    object ShowNoCashSize: IncomeUiEvent
}