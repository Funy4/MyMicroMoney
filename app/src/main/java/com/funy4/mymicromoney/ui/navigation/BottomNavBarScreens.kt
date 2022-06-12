package com.funy4.mymicromoney.ui.navigation

import com.funy4.mymicromoney.R

sealed class BottomNavBarScreens(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object Expenses : BottomNavBarScreens(
        route = "expenses",
        title = "Затраты",
        iconId = R.drawable.ic_expense
    )

    object Income : BottomNavBarScreens(
        route = "income",
        title = "Прибыль",
        iconId = R.drawable.ic_income
    )

    object Cash : BottomNavBarScreens(
        route = "cash",
        title = "Деньги",
        iconId = R.drawable.ic_cash
    )

    object Ai : BottomNavBarScreens(
        route = "ai",
        title = "AI",
        iconId = R.drawable.ic_ai
    )
}