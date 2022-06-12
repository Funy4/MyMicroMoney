package com.funy4.mymicromoney.ui.screens.income.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funy4.mymicromoney.Mocks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(

) : ViewModel() {

    private val _uiEvent = Channel<IncomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val incomeList = flow { emit(Mocks.incomeList) }
    val transactionList = flow { emit(Mocks.incomeWithTransactions) }

    val showAddIncome = mutableStateOf(false)

    fun onEvent(event: IncomeEvent) {
        when (event) {
            is IncomeEvent.OnDeleteIncome -> {}
            is IncomeEvent.OnIncomeClick -> {}
            IncomeEvent.OnAddIncomeClick -> {
                showAddIncome.value = true
            }
            IncomeEvent.OnCloseAddIncomeClick -> {
                showAddIncome.value = false
            }
            IncomeEvent.OnSaveIncomeClick -> {
                showAddIncome.value = false
            }
        }
    }


    private fun sendUiEvent(event: IncomeUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}