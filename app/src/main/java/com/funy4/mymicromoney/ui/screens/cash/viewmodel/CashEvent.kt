package com.funy4.mymicromoney.ui.screens.cash.viewmodel

import com.funy4.domain.model.CashModel

sealed interface CashEvent {
    data class OnDeleteCashClick(val cash: CashModel) : CashEvent
    object OnDismissAlertDialog : CashEvent
    object OnConfirmAlertDialog : CashEvent

    object OnCreateCashClick : CashEvent
    object OnCloseAddCashWindow: CashEvent
    data class OnChangeCreateCashIcon(val iconId: Int): CashEvent
    data class OnAddCashClick(val cashName: String, val baseCash: Double): CashEvent
    data class OnSelectIcon(val iconId: Int): CashEvent
    object OnAddCashIconClick: CashEvent
    object OnDismissSelectIconDialog: CashEvent
}