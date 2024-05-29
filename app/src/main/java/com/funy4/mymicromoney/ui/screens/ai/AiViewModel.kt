package com.funy4.mymicromoney.ui.screens.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funy4.domain.model.PredictModel
import com.funy4.domain.model.TransactionShortModel
import com.funy4.domain.usecase.predict.PredictDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor(
    private val predictUseCase: PredictDataUseCase,
) : ViewModel() {
    private val mState = MutableStateFlow<AiState>(AiState.EnterData)
    val state: StateFlow<AiState> = mState

    private val mEvents = Channel<AiEvent>()
    val events = mEvents.receiveAsFlow()


    fun handleAction(aiAction: AiAction) {
        when (aiAction) {
            is AiAction.OnDoPredictClick -> {
                viewModelScope.launch {
                    try {
                        mState.value = AiState.Loading
                        mState.value = AiState.Showdata(predictUseCase(aiAction.days))
                    } catch (e: Exception) {
                        mEvents.send(AiEvent.ShowServerErrorException)
                        mState.value = AiState.EnterData
                    }
                }
            }

            AiAction.OnReadyClick -> {
                mState.value = AiState.EnterData
            }
        }
    }
}
