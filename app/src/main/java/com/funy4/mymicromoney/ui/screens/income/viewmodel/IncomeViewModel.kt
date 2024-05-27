package com.funy4.mymicromoney.ui.screens.income.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funy4.domain.model.IncomeModel
import com.funy4.domain.model.IncomeWithTransactionsModel
import com.funy4.domain.usecase.cash.GetAllCashUseCase
import com.funy4.domain.usecase.income.AddIncomeUseCase
import com.funy4.domain.usecase.income.DeleteIncomeUseCase
import com.funy4.domain.usecase.income.GetAllIncomeUseCase
import com.funy4.domain.usecase.transactions.GetAllIncomeTransactionsUseCase
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
class IncomeViewModel @Inject constructor(
    private val getAllIncomeUseCase: GetAllIncomeUseCase,
    private val deleteIncomeUseCase: DeleteIncomeUseCase,
    private val getAllCashUseCase: GetAllCashUseCase,
    private val addIncomeUseCase: AddIncomeUseCase,
    private val getAllIncomeTransactionsUseCase: GetAllIncomeTransactionsUseCase
) : ViewModel() {

    private val _uiEvent = Channel<IncomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val incomeList = mutableStateOf<List<IncomeModel>>(listOf())
    val transactions = mutableStateOf<List<IncomeWithTransactionsModel>>(listOf())

    val showAddIncome = mutableStateOf(false)

    val cashListSize = mutableStateOf(0)
    val showDeleteDialog = mutableStateOf(false)

    val showSelectIconDialog = mutableStateOf(false)
    val selectedIconId = mutableStateOf(R.drawable.ic_1)
    var incomeIdForDelete: UUID? = null

    init {
        viewModelScope.launch {
            getAllIncomeTransactionsUseCase().collect {
                transactions.value = it
            }
        }
        viewModelScope.launch {
            getAllIncomeUseCase().collect {
                incomeList.value = it
            }
        }
        viewModelScope.launch {
            getAllCashUseCase().collect {
                cashListSize.value = it.size
            }
        }
    }

    fun onEvent(event: IncomeEvent) {
        when (event) {
            IncomeEvent.OnAcceptDeleteIncome -> {
                viewModelScope.launch {
                    incomeIdForDelete?.let { deleteIncomeUseCase(it) }
                }
            }

            is IncomeEvent.OnIncomeClick -> {
                if (cashListSize.value <= 0) {
                    sendUiEvent(IncomeUiEvent.ShowNoCashSize)
                }
                sendUiEvent(IncomeUiEvent.OpenAddTransactionScreen(event.item.id))
            }

            IncomeEvent.OnAddIncomeClick -> {
                showAddIncome.value = true
            }

            IncomeEvent.OnCloseAddIncomeClick -> {
                showAddIncome.value = false
            }

            is IncomeEvent.OnSaveIncomeClick -> {
                viewModelScope.launch {
                    addIncomeUseCase(
                        IncomeModel(
                            id = UUID.randomUUID(),
                            name = event.name,
                            iconId = selectedIconId.value,
                            color = Color(
                                red = Random.nextInt() % 254,
                                green = Random.nextInt() % 254,
                                blue = Random.nextInt() % 254,
                                alpha = 255
                            ).toArgb(),
                            money = 0.0
                        )
                    )
                }
                showAddIncome.value = false
            }

            IncomeEvent.OnCreateIncomeIconClick -> {
                showSelectIconDialog.value = true
            }

            is IncomeEvent.OnLongItemPress -> {
                incomeIdForDelete = event.item.id
                showDeleteDialog.value = true
            }

            IncomeEvent.OnDismissDeleteDialog -> {
                showDeleteDialog.value = false
            }

            IncomeEvent.OnCreateIncomeIconDismiss -> {
                showSelectIconDialog.value = false
            }

            is IncomeEvent.OnCreateIncomeSelectIcon -> {
                selectedIconId.value = event.iconId
                showSelectIconDialog.value = false
            }
        }
    }


    private fun sendUiEvent(event: IncomeUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}