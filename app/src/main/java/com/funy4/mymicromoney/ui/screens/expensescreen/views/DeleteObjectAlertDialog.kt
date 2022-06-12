package com.funy4.mymicromoney.ui.screens.expensescreen.views

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteObjectAlertDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {


    AlertDialog(
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text(text = "Да")
            }
        },
        onDismissRequest = { onDismiss() },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Отмена")
            }
        },
        title = { Text(text = "Подтвердите действие") },
        text = { Text(text = "Вы уверены, что хотите удалить объект?") }
    )
}