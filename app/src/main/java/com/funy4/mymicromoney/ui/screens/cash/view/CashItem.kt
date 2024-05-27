package com.funy4.mymicromoney.ui.screens.cash.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.funy4.mymicromoney.ui.screens.expensescreen.views.CloseIcon

@Composable
fun CashItem(
    modifier: Modifier = Modifier,
    title: String,
    money: Double,
    iconId: Int,
    onCrossClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colors.secondary),
        contentAlignment = Alignment.TopEnd
    ) {
        CloseIcon {
            onCrossClick()
        }
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(6f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painterResource(id = iconId), contentDescription = "cash icon",
                        tint = Color.White,
                        modifier = Modifier.size(42.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = title,
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    modifier = Modifier.weight(3f),
                    text = "$money ₽",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}
