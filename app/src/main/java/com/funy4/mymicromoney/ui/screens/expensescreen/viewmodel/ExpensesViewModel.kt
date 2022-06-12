package com.funy4.mymicromoney.ui.screens.expensescreen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funy4.domain.model.ExpenseModel
import com.funy4.domain.usecase.expenses.*
import com.funy4.domain.usecase.transactions.GetAllExpensesTransactionsUseCase
import com.funy4.mymicromoney.Mocks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val addExpensesUseCase: AddExpensesUseCase,
    private val deleteExpensesUseCase: DeleteExpensesUseCase,
    private val getAllExpensesUseCase: GetAllExpensesUseCase,
    private val updateExpensesUseCase: UpdateExpensesUseCase,
    private val getExpenseUseCase: GetExpenseUseCase,
    private val getAllExpensesTransactionsUseCase: GetAllExpensesTransactionsUseCase
) : ViewModel() {

    private var tempDeleteExpense: ExpenseModel? = null
    private val _uiEvent = Channel<ExpensesUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    //    val expensesList = getAllExpensesUseCase()
//    val transactionList = getAllExpensesTransactionsUseCase()
    val transactionList = flow { emit(Mocks.expensesWithTransactions) }
    val expensesList: Flow<List<ExpenseModel>> = flow { emit(Mocks.expensesList) }

    val showAddExpense = mutableStateOf(false)
    val showDeleteDialog = mutableStateOf(false)

    fun onEvent(event: ExpensesEvent) {
        when (event) {
            ExpensesEvent.OnAddExpenseClick -> {
                showAddExpense.value = true
//                sendUiEvent(ExpensesUiEvent.OpenAddExpenseScreen)
            }
            is ExpensesEvent.OnDeleteExpense -> {
//                sendUiEvent()
            }
            is ExpensesEvent.OnExpenseClick -> {
                sendUiEvent(ExpensesUiEvent.OpenAddTransactionScreen(event.item.id))
            }
            ExpensesEvent.OnCloseAddExpenseClick -> {
                println("close")
                showAddExpense.value = false
            }
            is ExpensesEvent.OnSaveExpenseClick -> {
                viewModelScope.launch {
//                    addExpensesUseCase(
//                        event.name,
//                        color = Color(
//                            red = Random().nextInt() % 254,
//                            green = Random().nextInt() % 254,
//                            blue = Random().nextInt() % 254,
//                            alpha = 255
//                        ).toArgb(),
//                        iconId = R.drawable.ic_expense
//                    )
                    showAddExpense.value = false
                }
            }
            is ExpensesEvent.OnLongPressExpense -> {
                tempDeleteExpense = event.item
                showDeleteDialog.value = true
            }
            ExpensesEvent.OnConfirmDeleteDialogClick -> {
                viewModelScope.launch {
                    tempDeleteExpense?.id?.let { deleteExpensesUseCase(it) }
                    showDeleteDialog.value = false
                }
            }
            ExpensesEvent.OnDismissDeleteDialogClick -> {
                showDeleteDialog.value = false
            }

        }
    }

    private fun sendUiEvent(event: ExpensesUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}