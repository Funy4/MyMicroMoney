package com.funy4.mymicromoney.ui.screens.ai

sealed interface AiAction {
    data class OnDoPredictClick(val days: Int): AiAction
    object OnReadyClick: AiAction
}