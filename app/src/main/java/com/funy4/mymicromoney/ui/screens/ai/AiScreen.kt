package com.funy4.mymicromoney.ui.screens.ai

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun AiScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Cyan)) { append("Я ") }
                withStyle(style = SpanStyle(color = Color.Magenta)) { append("ебал ") }
                withStyle(style = SpanStyle(color = Color.LightGray)) { append("в ") }
                withStyle(style = SpanStyle(color = Color.Green)) { append("рот") }
            }

        )
    }
}