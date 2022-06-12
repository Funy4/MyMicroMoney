package com.funy4.mymicromoney.ui.screens.income.viewmodel

import java.util.*

sealed interface IncomeUiEvent {
    data class OpenAddTransactionScreen(val id: UUID): IncomeUiEvent
}