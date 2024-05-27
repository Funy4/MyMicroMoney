package com.funy4.mymicromoney.ui.screens.addincome.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.funy4.domain.model.CashModel

@Composable
fun CurrentCash(modifier: Modifier = Modifier, cash: CashModel, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(150.dp)
            .background(
                Color.Green.copy(alpha = 0.04f),
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
                    painter = painterResource(id = cash.icon),
                    contentDescription = "expense icon",
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "${cash.money}₽",
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Text(
                text = cash.name,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}