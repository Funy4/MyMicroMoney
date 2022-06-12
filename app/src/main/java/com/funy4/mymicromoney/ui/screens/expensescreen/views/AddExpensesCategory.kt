package com.funy4.mymicromoney.ui.screens.expensescreen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun ExpensesTextField(modifier: Modifier = Modifier, onDoneClick: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = modifier
            .border(
                width = 3.dp,
                shape = RoundedCornerShape(30),
                color = MaterialTheme.colors.primary
            )
            .background(Color.Transparent),
        value = text,
        trailingIcon = {
            IconButton(
                onClick = {
                    onDoneClick(text)
                    keyboardController?.hide()
                }) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "add expense"
                )
            }
        },
        onValueChange = { inputText -> text = inputText },
        shape = RoundedCornerShape(30),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneClick(text)
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun AddCategoryCard(
    modifier: Modifier = Modifier,
    title: String,
    onConfirmClick: (String) -> Unit,
    cardBackgroundColor: Color = MaterialTheme.colors.background,
    onCloseClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(cardBackgroundColor),
        contentAlignment = Alignment.TopEnd
    ) {
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
                ExpensesTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    onDoneClick = { onConfirmClick(it) })
            }
        }
    }
}

@Composable
fun CloseIcon(modifier: Modifier = Modifier, onCloseClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = { onCloseClick() }
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close add",
            tint = Color.Black
        )
    }
}

