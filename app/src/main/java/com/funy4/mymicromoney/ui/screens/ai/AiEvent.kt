package com.funy4.mymicromoney.ui.screens.ai

sealed interface AiEvent {
    object ShowServerErrorException : AiEvent
}