package com.funy4.mymicromoney.ui.screens.addincome.view

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.funy4.mymicromoney.R

@Composable
fun AddIncomeTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Добавить доход") },
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