package com.funy4.mymicromoney.ui.screens.addincome.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funy4.domain.model.CashModel
import com.funy4.domain.model.IncomeModel
import com.funy4.domain.usecase.cash.GetAllCashUseCase
import com.funy4.domain.usecase.income.GetAllIncomeUseCase
import com.funy4.domain.usecase.transactions.AddTransactionIncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddIncomeViewModel @Inject constructor(
    private val getAllCashUseCase: GetAllCashUseCase,
    private val getAllIncomesUseCase: GetAllIncomeUseCase,
    private val addTransactionIncomeUseCase: AddTransactionIncomeUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val incomeList = mutableStateOf<List<IncomeModel>>(listOf())
    val cashList = mutableStateOf<List<CashModel>>(listOf())

    var cashSelected = mutableStateOf<CashModel?>(null)
    val incomesSelected = mutableStateOf<IncomeModel?>(null)
    var transactionName by mutableStateOf("")
    var money by mutableStateOf(0.0)

    var baseIncomeId = UUID.randomUUID()
    val isShowDatePicker = mutableStateOf(false)
    val selectedDateTime = mutableStateOf(LocalDate.now())

    val isShowSelectCash = mutableStateOf(false)
    val isShowSelectIncome = mutableStateOf(false)

    private val _uiEvent = Channel<AddIncomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            launch {
                getAllIncomesUseCase().collect {
                    incomeList.value = it
                    incomesSelected.value = it.find { it.id == baseIncomeId }
//                    if (IncomesSelected.value == null) IncomesSelected.value = it.firstOrNull()
                }
            }
            launch {
                getAllCashUseCase().collect {
                    cashList.value = it
                    if (cashSelected.value == null) cashSelected.value = it.firstOrNull()
                }
            }

        }
    }


    fun onEvent(event: AddIncomeEvent) {
        when (event) {
            AddIncomeEvent.OnBack -> sendUiEvent(AddIncomeUiEvent.PopBackStack)
            is AddIncomeEvent.OnCashChange -> {
                cashSelected.value = event.cashModel
                isShowSelectCash.value = false
            }

            is AddIncomeEvent.OnIncomeChange -> {
                incomesSelected.value = event.income
                isShowSelectIncome.value = false
            }

            is AddIncomeEvent.OnDateChange -> {
                selectedDateTime.value = event.date
            }

            is AddIncomeEvent.OnMoneyChange -> {
                money = event.money.toDouble()
            }

            is AddIncomeEvent.OnTransactionNameChange -> {
                transactionName = event.transactionName
            }

            is AddIncomeEvent.OnSaveIncomeClick -> {
                if (event.name.isEmpty() || event.cost == 0.0) {
                    sendUiEvent(AddIncomeUiEvent.FieldsIsEmpty)
                    return
                }
                saveIncome(event.name, event.cost)
                sendUiEvent(AddIncomeUiEvent.PopBackStack)
            }

            AddIncomeEvent.OnSelectDateClick -> {
                isShowDatePicker.value = true
            }

            AddIncomeEvent.OnDismissSelectDate -> {
                isShowDatePicker.value = false
            }

            AddIncomeEvent.OnChangeCashClick -> {
                isShowSelectCash.value = true
            }

            AddIncomeEvent.OnChangeIncomeClick -> {
                isShowSelectIncome.value = true
            }

            AddIncomeEvent.OnDismissChangeCash -> {
                isShowSelectCash.value = false
            }

            AddIncomeEvent.OnDismissChangeIncome -> {
                isShowSelectIncome.value = false
            }

            is AddIncomeEvent.InitBaseIncome -> {
                baseIncomeId = event.id
            }
        }
    }

    private fun saveIncome(name: String, cost: Double) {
        viewModelScope.launch {
            addTransactionIncomeUseCase(
                name = name,
                cost = cost,
                cashId = cashSelected.value!!.id,
                date = selectedDateTime.value,
                incomeId = incomesSelected.value?.id ?: throw RuntimeException("Income not found")
            )
        }
    }

    private fun sendUiEvent(event: AddIncomeUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}