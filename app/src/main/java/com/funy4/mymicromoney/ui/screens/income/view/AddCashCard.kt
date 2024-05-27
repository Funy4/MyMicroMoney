package com.funy4.mymicromoney.ui.screens.income.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.screens.expensescreen.views.CloseIcon
import com.funy4.mymicromoney.ui.screens.expensescreen.views.ExpensesTextField

@Composable
fun AddCashCard(
    modifier: Modifier = Modifier,
    title: String,
    selectedIconId: Int,
    onConfirmClick: (String, Double) -> Unit,
    cardBackgroundColor: Color = MaterialTheme.colors.background,
    onCloseClick: () -> Unit,
    onIconClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(cardBackgroundColor),
        contentAlignment = Alignment.TopEnd
    ) {
        var cashText by remember { mutableStateOf("") }
        CloseIcon {
            onCloseClick()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = title,
                    color = Color.Black, fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = selectedIconId),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onIconClick() }
                            .size(42.dp))
                    Column {
                        ExpensesTextField(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            onDoneClick = {
                                onConfirmClick(
                                    it,
                                    cashText.replace(",", ".").let { cash ->
                                        if (cash.isNotEmpty()) cash.toDouble()
                                        else 0.0
                                    }
                                )
                            })
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextField(
                            modifier = Modifier
                                .height(55.dp)
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .border(
                                    width = 3.dp,
                                    shape = RoundedCornerShape(30),
                                    color = MaterialTheme.colors.primary
                                ),
                            value = cashText,
                            onValueChange = { inputText -> cashText = inputText },
                            shape = RoundedCornerShape(30),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text("Начальный баланс") },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }

                }

            }
        }
    }

}


