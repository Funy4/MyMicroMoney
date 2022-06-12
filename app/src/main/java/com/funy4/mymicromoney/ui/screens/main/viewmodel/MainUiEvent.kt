package com.funy4.mymicromoney.ui.screens.main.viewmodel

sealed interface MainUiEvent {
    data class Navigate(val route: String): MainUiEvent
}