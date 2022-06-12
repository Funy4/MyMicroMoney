package com.funy4.mymicromoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.funy4.mymicromoney.ui.screens.main.MainScreen
import com.funy4.mymicromoney.ui.theme.MyMicroMoneyTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMicroMoneyTheme(darkTheme = false) {
                MainScreen()
            }
        }
    }
}