package com.funy4.mymicromoney.ui.screens.addexpense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.funy4.domain.model.CashModel
import com.funy4.domain.model.ExpenseModel
import com.funy4.mymicromoney.Mocks
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.screens.addexpense.viewmodel.AddExpenseEvent
import com.funy4.mymicromoney.ui.screens.addexpense.viewmodel.AddExpenseUiEvent
import com.funy4.mymicromoney.ui.screens.addexpense.viewmodel.AddExpenseViewModel
import com.funy4.mymicromoney.ui.theme.MyMicroMoneyTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddExpenseTransaction(
    modifier: Modifier = Modifier,
    viewModel: AddExpenseViewModel = hiltViewModel(),
    navController: NavController,
) {
    var transactionName by remember { mutableStateOf(TextFieldValue("")) }
    var transactionNameIsError = false
    var costText by remember { mutableStateOf(TextFieldValue("")) }
    var costTextIsError = false

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                AddExpenseUiEvent.PopBackStack -> navController.popBackStack()
            }
        }
    }

    Scaffold(topBar = {
        AddExpenseTopBar {
            viewModel.onEvent(AddExpenseEvent.OnBack)
        }
    }) {
        Box(
            modifier = modifier.padding(it)
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            Column(Modifier.fillMaxSize()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CurrentExpense(expense = Mocks.expensesList.first())
                    CurrentCash(cash = Mocks.cashList.first())
                }
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = transactionName,
                    label = { Text(text = "Transaction name") },
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
                    label = { Text(text = "Cost") },
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
                        onClick = { },
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
                        onClick = {}) {
                        Text(text = "Save transaction")
                    }
                }
            }
        }
    }
}

@Composable
fun AddExpenseTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Add expense") },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun AddExpensesFields() {
    var transactionName by remember { mutableStateOf(TextFieldValue("")) }
    var transactionNameIsError = false
    var costText by remember { mutableStateOf(TextFieldValue("")) }
    var costTextIsError = false

    TopAppBar(title = { Text(text = "Add") })


    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Column(Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CurrentExpense(expense = Mocks.expensesList.first())
                CurrentCash(cash = Mocks.cashList.first())
            }
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = transactionName,
                label = { Text(text = "Transaction name") },
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
                label = { Text(text = "Cost") },
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
                    onClick = { },
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
                    onClick = {}) {
                    Text(
                        text = "Save transaction",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun CurrentExpense(modifier: Modifier = Modifier, expense: ExpenseModel) {
    Box(
        modifier = modifier
            .size(150.dp)
            .background(
                Color.Red.copy(alpha = 0.04f),
                RoundedCornerShape(20)
            )
            .clip(RoundedCornerShape(20))
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = modifier.size(60.dp),
                    painter = painterResource(id = expense.iconId),
                    contentDescription = "expense icon",
                    tint = Color(expense.color)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "${expense.money}$",
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Text(
                text = expense.name + "fdsddsadasfdsfsdfasdfasf",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CurrentCash(modifier: Modifier = Modifier, cash: CashModel) {
    Box(
        modifier = modifier
            .size(150.dp)
            .background(
                Color.Green.copy(alpha = 0.04f),
                RoundedCornerShape(20)
            )
            .clip(RoundedCornerShape(20))
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = modifier.size(60.dp),
                    painter = painterResource(id = cash.icon),
                    contentDescription = "expense icon",
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "${cash.money}$",
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Text(
                text = cash.name + "fdsddsadasfdsfsdfasdfasf",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun AddExpensesPreview() {
    MyMicroMoneyTheme {
        Box(Modifier.fillMaxSize()) {
            AddExpensesFields()
//            CurrentExpense(expense = Mocks.expensesList.first())
        }
    }
}