package com.funy4.mymicromoney.ui.screens.addexpense

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.funy4.domain.model.CashModel
import com.funy4.domain.model.ExpenseModel
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.screens.addexpense.view.AddExpenseTopBar
import com.funy4.mymicromoney.ui.screens.addexpense.view.CurrentCash
import com.funy4.mymicromoney.ui.screens.addexpense.view.CurrentExpense
import com.funy4.mymicromoney.ui.screens.addexpense.viewmodel.AddExpenseEvent
import com.funy4.mymicromoney.ui.screens.addexpense.viewmodel.AddExpenseUiEvent
import com.funy4.mymicromoney.ui.screens.addexpense.viewmodel.AddExpenseViewModel
import java.time.LocalDate
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddExpenseTransaction(
    modifier: Modifier = Modifier,
    viewModel: AddExpenseViewModel = hiltViewModel(),
    navController: NavController,
    baseExpenseId: UUID
) {
    var transactionName by remember { mutableStateOf(TextFieldValue("")) }
    var transactionNameIsError = false
    var costText by remember { mutableStateOf(TextFieldValue("")) }
    var costTextIsError = false
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(AddExpenseEvent.InitBaseExpense(baseExpenseId))
        viewModel.uiEvent.collect { event ->
            when (event) {
                AddExpenseUiEvent.PopBackStack -> navController.popBackStack()
                AddExpenseUiEvent.FieldsIsEmpty -> Toast.makeText(
                    context,
                    "Не все поля заполнены!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(topBar = {
        AddExpenseTopBar {
            viewModel.onEvent(AddExpenseEvent.OnBack)
        }
    }) {
        Box(
            modifier = modifier
                .padding(it)
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            Column(Modifier.fillMaxSize()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    viewModel.expensesSelected.value?.let {
                        CurrentExpense(expense = it) { viewModel.onEvent(AddExpenseEvent.OnChangeExpenseClick) }
                    } ?: CircularProgressIndicator(
                        modifier = Modifier
                            .size(150.dp)
                            .padding(50.dp)
                    )
                    viewModel.cashSelected.value?.let {
                        CurrentCash(cash = it) { viewModel.onEvent(AddExpenseEvent.OnChangeCashClick) }
                    } ?: CircularProgressIndicator(
                        modifier = Modifier
                            .size(150.dp)
                            .padding(50.dp)
                    )
                }
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = transactionName,
                    label = { Text(text = "Название транзакции") },
                    onValueChange = {
                        if (it.text.isBlank()) {
                            transactionName = it
                            transactionNameIsError = true
                        } else {
                            transactionName = it
                            transactionNameIsError = false
                        }
                    },
                    isError = transactionNameIsError
                )
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = costText,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Стоимость") },
                    onValueChange = {
                        if (it.text.isBlank()) {
                            costText = it
                            costTextIsError = true
                        } else {
                            costText = it
                            costTextIsError = false
                        }
                    },
                    isError = costTextIsError
                )
                Spacer(Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        modifier = Modifier
                            .weight(2f)
                            .height(60.dp),
                        onClick = { viewModel.onEvent(AddExpenseEvent.OnSelectDateClick) },
                        shape = RoundedCornerShape(20)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Select date",
                        )
                    }
                    Spacer(Modifier.width(20.dp))
                    Button(modifier = Modifier
                        .height(60.dp)
                        .weight(4f),
                        shape = RoundedCornerShape(20),
                        onClick = {
                            viewModel.onEvent(
                                AddExpenseEvent.OnSaveExpenseClick(
                                    transactionName.text,
                                    costText.text.replace(",", ".").let {
                                        if (it.isEmpty()) 0.0 else it.toDouble()
                                    }
                                )
                            )
                        }) {
                        Text(text = "Сохранить", fontSize = 18.sp)
                    }
                }
            }
        }
        if (viewModel.isShowDatePicker.value) {
            val context = LocalContext.current
            val currentDate = LocalDate.now()

            DatePickerDialog(
                context,
                { picker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    viewModel.onEvent(AddExpenseEvent.OnDateChange(selectedDate))
                },
                currentDate.year,
                currentDate.monthValue - 1,
                currentDate.dayOfMonth
            ).apply {
                setOnDismissListener {
                    viewModel.onEvent(AddExpenseEvent.OnDismissSelectDate)
                }
            }.show()
        }
        if (viewModel.isShowSelectExpense.value) {
           ExpensesSelectorDialog(
                expenses = viewModel.expensesList.value,
                onDismissRequest = { viewModel.onEvent(AddExpenseEvent.OnDismissChangeExpense) }) {
                viewModel.onEvent(AddExpenseEvent.OnExpenseChange(it))
            }
        }
        if (viewModel.isShowSelectCash.value) {
            CashSelectorDialog(
                cashList = viewModel.cashList.value,
                onDismissRequest = { viewModel.onEvent(AddExpenseEvent.OnDismissChangeCash) }) {
                viewModel.onEvent(AddExpenseEvent.OnCashChange(it))
            }
        }
    }
}

@Composable
fun CashSelectorDialog(
    cashList: List<CashModel>,
    onDismissRequest: () -> Unit,
    onIconSelected: (CashModel) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Выберите счет", fontSize = 18.sp)
        },
        text = {
            Spacer(modifier = Modifier.padding(16.dp))
            LazyColumn {
                items(cashList) { cash ->
                    Spacer(modifier = Modifier.width(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onIconSelected(cash) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = cash.icon),
                            contentDescription = null,
                            modifier = Modifier.size(42.dp),
                            tint = Color.Black

                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(cash.name, fontSize = 18.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("Отмена")
            }
        }
    )
}

@Composable
fun ExpensesSelectorDialog(
    expenses: List<ExpenseModel>,
    onDismissRequest: () -> Unit,
    onIconSelected: (ExpenseModel) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Выберите категорию трат", fontSize = 18.sp)
        },
        text = {
            LazyColumn {
                items(expenses) { expense ->
                    Spacer(modifier = Modifier.width(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onIconSelected(expense) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = expense.iconId),
                            contentDescription = null,
                            modifier = Modifier.size(42.dp),
                            tint = Color(expense.color)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(expense.name, fontSize = 18.sp, color = Color.Black)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("Отмена")
            }
        }
    )
}
