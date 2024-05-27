package com.funy4.mymicromoney.ui.screens.addexpense.viewmodel


sealed interface AddExpenseUiEvent {
    object PopBackStack: AddExpenseUiEvent
    object FieldsIsEmpty: AddExpenseUiEvent
}