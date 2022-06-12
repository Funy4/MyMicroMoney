package com.funy4.mymicromoney.ui.screens.cash.viewmodel

import com.funy4.domain.model.CashModel

sealed interface CashEvent {
    data class OnDeleteCashClick(val cash: CashModel) : CashEvent
    object OnDismissAlertDialog : CashEvent
    object OnConfirmAlertDialog : CashEvent
}