package com.funy4.mymicromoney.ui.screens.income.viewmodel

import com.funy4.domain.model.IncomeModel
import java.util.*

sealed interface IncomeEvent {
    data class OnIncomeClick(val item: IncomeModel) : IncomeEvent
    object OnAddIncomeClick : IncomeEvent
    object OnCloseAddIncomeClick : IncomeEvent
    object OnSaveIncomeClick : IncomeEvent
    data class OnDeleteIncome(val id: UUID) : IncomeEvent
    data class OnLongItemPress(val item: IncomeModel) : IncomeEvent
}