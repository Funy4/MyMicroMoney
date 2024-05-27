package com.funy4.mymicromoney.ui.screens.addexpense.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.funy4.domain.model.ExpenseModel

@Composable
fun CurrentExpense(modifier: Modifier = Modifier, expense: ExpenseModel, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(150.dp)
            .background(
                Color.Red.copy(alpha = 0.04f),
                RoundedCornerShape(20)
            )
            .clip(RoundedCornerShape(20))
            .clickable(onClick = onClick)
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
                    text = "${expense.money}₽",
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Text(
                text = expense.name,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}