package com.funy4.mymicromoney.ui.screens.addexpense.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funy4.domain.model.CashModel
import com.funy4.domain.model.ExpenseModel
import com.funy4.domain.usecase.cash.GetAllCashUseCase
import com.funy4.domain.usecase.expenses.GetAllExpensesUseCase
import com.funy4.domain.usecase.transactions.AddTransactionExpenseUseCase
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
class AddExpenseViewModel @Inject constructor(
    private val getAllCashUseCase: GetAllCashUseCase,
    private val getAllExpensesUseCase: GetAllExpensesUseCase,
    private val addTransactionExpenseUseCase: AddTransactionExpenseUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val expensesList = mutableStateOf<List<ExpenseModel>>(listOf())
    val cashList = mutableStateOf<List<CashModel>>(listOf())

    var cashSelected = mutableStateOf<CashModel?>(null)
    val expensesSelected = mutableStateOf<ExpenseModel?>(null)
    var transactionName by mutableStateOf("")
    var money by mutableStateOf(0.0)

    var baseExpenseId = UUID.randomUUID()
    val isShowDatePicker = mutableStateOf(false)
    val selectedDateTime = mutableStateOf(LocalDate.now())

    val isShowSelectCash = mutableStateOf(false)
    val isShowSelectExpense = mutableStateOf(false)

    private val _uiEvent = Channel<AddExpenseUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            launch {
                getAllExpensesUseCase().collect {
                    expensesList.value = it
                    expensesSelected.value = it.find { it.id == baseExpenseId }
//                    if (expensesSelected.value == null) expensesSelected.value = it.firstOrNull()
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


    fun onEvent(event: AddExpenseEvent) {
        when (event) {
            AddExpenseEvent.OnBack -> sendUiEvent(AddExpenseUiEvent.PopBackStack)
            is AddExpenseEvent.OnCashChange -> {
                cashSelected.value = event.cashModel
                isShowSelectCash.value = false
            }

            is AddExpenseEvent.OnExpenseChange -> {
                expensesSelected.value = event.expense
                isShowSelectExpense.value = false
            }

            is AddExpenseEvent.OnDateChange -> {
                selectedDateTime.value = event.date
            }

            is AddExpenseEvent.OnMoneyChange -> {
                money = event.money.toDouble()
            }

            is AddExpenseEvent.OnTransactionNameChange -> {
                transactionName = event.transactionName
            }

            is AddExpenseEvent.OnSaveExpenseClick -> {
                if (event.name.isEmpty() || event.cost == 0.0) {
                    sendUiEvent(AddExpenseUiEvent.FieldsIsEmpty)
                    return
                }
                saveExpense(event.name, event.cost)
                sendUiEvent(AddExpenseUiEvent.PopBackStack)
            }

            AddExpenseEvent.OnSelectDateClick -> {
                isShowDatePicker.value = true
            }

            AddExpenseEvent.OnDismissSelectDate -> {
                isShowDatePicker.value = false
            }

            AddExpenseEvent.OnChangeCashClick -> {
                isShowSelectCash.value = true
            }

            AddExpenseEvent.OnChangeExpenseClick -> {
                isShowSelectExpense.value = true
            }

            AddExpenseEvent.OnDismissChangeCash -> {
                isShowSelectCash.value = false
            }

            AddExpenseEvent.OnDismissChangeExpense -> {
                isShowSelectExpense.value = false
            }

            is AddExpenseEvent.InitBaseExpense -> {
                baseExpenseId = event.id
            }
        }
    }

    private fun saveExpense(name: String, cost: Double) {
        viewModelScope.launch {
            addTransactionExpenseUseCase(
                name = name,
                cost = cost,
                cashId = cashSelected.value!!.id,
                date = selectedDateTime.value,
                expenseId = expensesSelected.value?.id ?: throw RuntimeException("Expense not found")
            )
        }
    }

    private fun sendUiEvent(event: AddExpenseUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}