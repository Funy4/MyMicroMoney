package com.funy4.mymicromoney.ui.screens.main.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funy4.domain.usecase.cash.GetSumOfCashUseCase
import com.funy4.mymicromoney.Mocks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCashSumOfCashUseCase: GetSumOfCashUseCase
) : ViewModel() {

    private val _uiEvent = Channel<MainUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val allCosts = mutableStateOf<Double>(0.0)

    init {
        viewModelScope.launch {
            getCashSumOfCashUseCase().collect {
                allCosts.value = it ?: 0.0
            }
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnNavigateScreenClick -> {
                sendUiEvent(MainUiEvent.Navigate(route = event.route))
            }
        }
    }

    private fun sendUiEvent(event: MainUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}