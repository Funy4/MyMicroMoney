package com.funy4.mymicromoney.ui.screens.income.viewmodel

import com.funy4.domain.model.IncomeModel
import java.util.*

sealed interface IncomeEvent {
    data class OnIncomeClick(val item: IncomeModel) : IncomeEvent
    object OnAddIncomeClick : IncomeEvent
    object OnCloseAddIncomeClick : IncomeEvent
    data class OnSaveIncomeClick(val name: String) : IncomeEvent
    object OnAcceptDeleteIncome : IncomeEvent
    data class OnLongItemPress(val item: IncomeModel) : IncomeEvent
    object OnDismissDeleteDialog : IncomeEvent

    object OnCreateIncomeIconClick : IncomeEvent
    object OnCreateIncomeIconDismiss: IncomeEvent
    data class OnCreateIncomeSelectIcon(val iconId: Int) : IncomeEvent
}