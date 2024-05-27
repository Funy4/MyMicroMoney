package com.funy4.mymicromoney.ui.screens.ai

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funy4.data.api.ApiService
import com.funy4.domain.usecase.predict.PredictDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor(
    private val predictUseCase: PredictDataUseCase,
) : ViewModel() {
    private val mState = MutableStateFlow<AiState>(AiState.EnterData)
    val state: StateFlow<AiState> = mState

    fun handleAction(action: Action) {
        when (action) {
            is Action.OnDoPredictClick -> {
                viewModelScope.launch {
                    mState.value = AiState.Loading
                    mState.value = AiState.Showdata(predictUseCase(action.days))
                }
            }
        }
    }
}
