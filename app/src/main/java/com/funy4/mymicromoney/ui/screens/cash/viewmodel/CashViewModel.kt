package com.funy4.mymicromoney.ui.screens.cash.viewmodel

import android.util.Log
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
class CashViewModel @Inject constructor(

) : ViewModel() {

    private val _uiEvent = Channel<CashUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val cashList = flow { emit(Mocks.cashList) }
    val showDeleteCashDialog = mutableStateOf(false)

    fun onEvent(event: CashEvent){
        when(event){
            is CashEvent.OnDeleteCashClick -> {
                Log.d("Cash", "onDelete")
                showDeleteCashDialog.value = true
            }
            CashEvent.OnConfirmAlertDialog -> {
                showDeleteCashDialog.value = false
            }
            CashEvent.OnDismissAlertDialog -> {
                showDeleteCashDialog.value = false
            }
        }
    }

    private fun sendEvent(event: CashUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}