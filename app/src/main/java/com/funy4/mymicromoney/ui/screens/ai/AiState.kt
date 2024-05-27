package com.funy4.mymicromoney.ui.screens.ai

import com.funy4.domain.model.PredictModel
import com.funy4.domain.model.TransactionShortModel
import kotlinx.coroutines.flow.StateFlow

sealed interface AiState {
    object EnterData : AiState
    object Loading : AiState
    data class Showdata(val predict: PredictModel) : AiState
}