package com.funy4.mymicromoney.ui.screens.cash.viewmodel

import android.util.Log
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.funy4.domain.model.CashModel
import com.funy4.domain.repo.CashRepo
import com.funy4.domain.usecase.cash.AddCashUseCase
import com.funy4.domain.usecase.cash.DeleteCashUseCase
import com.funy4.domain.usecase.cash.GetAllCashUseCase
import com.funy4.mymicromoney.Mocks
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.getCashIconDrawableId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CashViewModel @Inject constructor(
    private val addCashUseCase: AddCashUseCase,
    private val getAllCashUseCase: GetAllCashUseCase,
    private val deleteCashUseCase: DeleteCashUseCase
) : ViewModel() {

    private val _uiEvent = Channel<CashUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    val showAddCashWindow = mutableStateOf(false)

    val cashList = mutableStateOf<List<CashModel>>(listOf())
    val showDeleteCashDialog = mutableStateOf(false)
    val showSelectIconDialog = mutableStateOf(false)
    val selectedIcon = mutableStateOf(R.drawable.ic_cash_1)
    var cashIdForDelete: UUID? = null

    init {
        viewModelScope.launch {
            getAllCashUseCase().collect {
                cashList.value = it
            }
        }
    }

    fun onEvent(event: CashEvent) {
        when (event) {
            is CashEvent.OnDeleteCashClick -> {
                showDeleteCashDialog.value = true
                cashIdForDelete = event.cash.id
            }

            CashEvent.OnConfirmAlertDialog -> {
                viewModelScope.launch {
                    showDeleteCashDialog.value = false
                    cashIdForDelete?.let { deleteCashUseCase(it) }
                }
            }

            CashEvent.OnDismissAlertDialog -> {
                showDeleteCashDialog.value = false
            }

            CashEvent.OnCreateCashClick -> {
                showAddCashWindow.value = true
            }

            is CashEvent.OnAddCashClick -> {
                viewModelScope.launch {
                    addCashUseCase(
                        CashModel(
                            UUID.randomUUID(),
                            name = event.cashName,
                            icon = selectedIcon.value,
                            money = event.baseCash
                        )
                    )
                    showAddCashWindow.value = false
                }
            }

            CashEvent.OnCloseAddCashWindow -> {
                showAddCashWindow.value = false
            }

            is CashEvent.OnChangeCreateCashIcon -> {
                selectedIcon.value = getCashIconDrawableId(event.iconId)
            }

            CashEvent.OnDismissSelectIconDialog -> {
                showSelectIconDialog.value = false
            }

            CashEvent.OnAddCashIconClick -> {
                showSelectIconDialog.value = true
            }

            is CashEvent.OnSelectIcon -> {
                selectedIcon.value = event.iconId
            }

        }
    }

    private fun sendEvent(event: CashUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}