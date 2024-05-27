package com.funy4.mymicromoney.ui.screens.addexpense.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.funy4.mymicromoney.Mocks
import com.funy4.mymicromoney.R

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
                CurrentExpense(expense = Mocks.expensesList.first(),) {}
                CurrentCash(cash = Mocks.cashList.first(),) {}
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
