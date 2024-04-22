package com.funy4.mymicromoney.ui.screens.ai

sealed interface Action {
    data class OnDoPredictClick(val days: Int): Action
}