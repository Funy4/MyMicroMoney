package com.funy4.mymicromoney.ui.screens.addincome.viewmodel

sealed interface AddIncomeUiEvent {
    object PopBackStack : AddIncomeUiEvent
    object FieldsIsEmpty : AddIncomeUiEvent
}