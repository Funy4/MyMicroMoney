package com.funy4.mymicromoney.ui.screens.expensescreen.viewmodel

import com.funy4.domain.model.ExpenseModel
import java.util.*

interface ExpensesEvent {
    object OnAddExpenseClick : ExpensesEvent
    object OnCloseAddExpenseClick : ExpensesEvent
    object OnConfirmDeleteDialogClick : ExpensesEvent
    object OnDismissDeleteDialogClick : ExpensesEvent
    data class OnExpenseClick(val item: ExpenseModel) : ExpensesEvent
    data class OnLongPressExpense(val item: ExpenseModel) : ExpensesEvent
    data class OnSaveExpenseClick(val name: String) : ExpensesEvent
    data class OnDeleteExpense(val id: UUID) : ExpensesEvent

    object OnSelectIconClick: ExpensesEvent
    object OnDismissSelectedIcon: ExpensesEvent
    data class OnSelectNewIcon(val iconId: Int): ExpensesEvent
}