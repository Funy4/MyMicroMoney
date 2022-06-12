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
import com.funy4.domain.model.type.TransactionType
import com.funy4.domain.usecase.cash.GetAllCashUseCase
import com.funy4.domain.usecase.expenses.GetAllExpensesUseCase
import com.funy4.domain.usecase.transactions.AddTransactionUseCase
import com.funy4.mymicromoney.Mocks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val getAllCashUseCase: GetAllCashUseCase,
    private val getAllExpensesUseCase: GetAllExpensesUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    //    val cashList = getAllCashUseCase()
//    val expensesList = getAllExpensesUseCase()
    val expensesList = flow { emit(Mocks.expensesList) }
    val cashList = flow { emit(Mocks.cashList) }

    var date by mutableStateOf(LocalDate.now())
    var cashSelected by mutableStateOf<CashModel?>(null)
    var expensesSelected by mutableStateOf<ExpenseModel?>(null)
    var transactionName by mutableStateOf("")
    var money by mutableStateOf(0.0)

    private val _uiEvent = Channel<AddExpenseUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddExpenseEvent) {
        when (event) {
            AddExpenseEvent.OnBack -> sendUiEvent(AddExpenseUiEvent.PopBackStack)
            is AddExpenseEvent.OnCashChange -> cashSelected = event.cashModel
            is AddExpenseEvent.OnExpenseChange -> expensesSelected = event.expense
            is AddExpenseEvent.OnDateChange -> date = event.date
            is AddExpenseEvent.OnMoneyChange -> money = event.money.toDouble()
            is AddExpenseEvent.OnTransactionNameChange -> transactionName = event.transactionName
            AddExpenseEvent.SaveExpense -> {
                saveExpense()
                sendUiEvent(AddExpenseUiEvent.PopBackStack)
            }

        }
    }

    init {
        viewModelScope.launch {
            val expenseId = savedStateHandle.get<String>("expenseId")
            cashSelected = cashList.first().first()
//            expensesSelected =
        }
    }

    private fun saveExpense() {
        viewModelScope.launch {
            addTransactionUseCase(
                name = transactionName,
                cost = money,
                cashId = cashSelected!!.id,
                transactionType = TransactionType.EXPENSE,
                date = date
            )
        }
    }

    private fun sendUiEvent(event: AddExpenseUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}