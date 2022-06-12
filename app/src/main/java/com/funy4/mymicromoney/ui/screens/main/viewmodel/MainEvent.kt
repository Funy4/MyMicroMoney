package com.funy4.mymicromoney.ui.screens.main.viewmodel

sealed interface MainEvent {
    data class OnNavigateScreenClick(val route: String) : MainEvent
}