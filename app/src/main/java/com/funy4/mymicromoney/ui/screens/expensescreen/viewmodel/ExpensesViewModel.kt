package com.funy4.mymicromoney.ui.screens.expensescreen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.funy4.domain.model.ExpenseModel
import com.funy4.domain.model.ExpenseWithTransactionsModel
import com.funy4.domain.usecase.cash.GetAllCashUseCase
import com.funy4.domain.usecase.expenses.*
import com.funy4.domain.usecase.transactions.GetAllExpensesTransactionsUseCase
import com.funy4.mymicromoney.Mocks
import com.funy4.mymicromoney.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val addExpensesUseCase: AddExpensesUseCase,
    private val deleteExpensesUseCase: DeleteExpensesUseCase,
    private val getAllExpensesUseCase: GetAllExpensesUseCase,
    private val updateExpensesUseCase: UpdateExpensesUseCase,
    private val getExpenseUseCase: GetExpenseUseCase,
    private val getAllCashUseCase: GetAllCashUseCase,
    private val getAllExpensesTransactions: GetAllExpensesTransactionsUseCase
) : ViewModel() {

    private val _uiEvent = Channel<ExpensesUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val transactionList = mutableStateOf<List<ExpenseWithTransactionsModel>>(listOf())
    val expensesList = mutableStateOf<List<ExpenseModel>>(listOf())

    val cashListSize = mutableStateOf(0)
    val showAddExpense = mutableStateOf(false)
    val showDeleteDialog = mutableStateOf(false)

    val showSelectIconDialog = mutableStateOf(false)
    val selectedIconId = mutableStateOf(R.drawable.ic_1)
    var expenceIdForDelete: UUID? = null


    init {
        viewModelScope.launch {
            getAllExpensesTransactions().collect{
                transactionList.value = it
            }
        }
        viewModelScope.launch {
            getAllCashUseCase().collect {
                cashListSize.value = it.size
            }
        }
        viewModelScope.launch {
            getAllExpensesUseCase().collect {
                expensesList.value = it
            }
        }
    }

    fun onEvent(event: ExpensesEvent) {
        when (event) {
            ExpensesEvent.OnAddExpenseClick -> {
                showAddExpense.value = true
//                sendUiEvent(ExpensesUiEvent.OpenAddExpenseScreen)
            }

            is ExpensesEvent.OnDeleteExpense -> {
                viewModelScope.launch {
                    deleteExpensesUseCase(event.id)
                }
            }

            is ExpensesEvent.OnExpenseClick -> {
                if (cashListSize.value <= 0) {
                    sendUiEvent(ExpensesUiEvent.ShowNoCashSize)
                } else {
                    sendUiEvent(ExpensesUiEvent.OpenAddTransactionScreen(event.item.id))
                }
            }

            ExpensesEvent.OnCloseAddExpenseClick -> {
                showAddExpense.value = false
            }

            is ExpensesEvent.OnSaveExpenseClick -> {
                viewModelScope.launch {
                    addExpensesUseCase(
                        event.name,
                        color = Color(
                            red = Random.nextInt() % 254,
                            green = Random.nextInt() % 254,
                            blue = Random.nextInt() % 254,
                            alpha = 255
                        ).toArgb(),
                        iconId = selectedIconId.value
                    )
                    showAddExpense.value = false
                }
            }

            is ExpensesEvent.OnLongPressExpense -> {
                expenceIdForDelete = event.item.id
                showDeleteDialog.value = true
            }

            ExpensesEvent.OnConfirmDeleteDialogClick -> {
                viewModelScope.launch {
                    expenceIdForDelete?.let { deleteExpensesUseCase(it) }
                    showDeleteDialog.value = false
                }
            }

            ExpensesEvent.OnDismissDeleteDialogClick -> {
                showDeleteDialog.value = false
            }

            ExpensesEvent.OnSelectIconClick -> {
                showSelectIconDialog.value = true
            }

            ExpensesEvent.OnDismissSelectedIcon -> {
                showSelectIconDialog.value = false
                selectedIconId.value = R.drawable.ic_1
            }

            is ExpensesEvent.OnSelectNewIcon -> {
                selectedIconId.value = event.iconId
                showSelectIconDialog.value = false
            }
        }
    }

    private fun sendUiEvent(event: ExpensesUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}