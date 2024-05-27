package com.funy4.mymicromoney.ui.navigation

sealed class Screen(val route: String) {
    object AddExpense : Screen(route = "expense")
    object AddIncome: Screen(route = "income")
}