package com.funy4.mymicromoney.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.funy4.mymicromoney.R

// Set of Material typography styles to start with
private val Roboto = FontFamily(
    Font(R.font.roboto_regular),
    Font(R.font.roboto_light, weight = FontWeight.Light),
    Font(R.font.roboto_bold, weight = FontWeight.Bold),
    Font(R.font.roboto_italic, style = FontStyle.Italic),
    Font(R.font.roboto_black, weight = FontWeight.Black)
)

val Typography = Typography(
    defaultFontFamily = Roboto,

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)