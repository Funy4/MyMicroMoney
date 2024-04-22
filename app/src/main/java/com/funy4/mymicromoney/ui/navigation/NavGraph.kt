@file:OptIn(ExperimentalAnimationApi::class)

package com.funy4.mymicromoney.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.funy4.mymicromoney.ui.screens.addexpense.AddExpenseTransaction
import com.funy4.mymicromoney.ui.screens.ai.AiScreen
import com.funy4.mymicromoney.ui.screens.cash.CashScreen
import com.funy4.mymicromoney.ui.screens.expensescreen.ExpensesScreen
import com.funy4.mymicromoney.ui.screens.income.IncomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController, padding: PaddingValues) {
    AnimatedNavHost(
        navController = navController,
        startDestination = BottomNavBarScreens.Ai.route
    ) {
        composable(
            route = BottomNavBarScreens.Cash.route,
            popEnterTransition = {
                fadeIn(animationSpec = tween(100))
            },

        ) {
            CashScreen(
                modifier = Modifier.padding(padding),
                navController = navController
            )
        }
        composable(route = BottomNavBarScreens.Expenses.route,
            popEnterTransition = {
                fadeIn(animationSpec = tween(100))
            },

            ) {
            ExpensesScreen(
                navController = navController,
                modifier = Modifier.padding(padding),
            )
        }
        composable(route = BottomNavBarScreens.Income.route,
            popEnterTransition = {
                fadeIn(animationSpec = tween(100))
            },
            ) {
            IncomeScreen(
                modifier = Modifier.padding(padding),
                navController = navController
            )
        }
        composable(route = BottomNavBarScreens.Ai.route){
            AiScreen(modifier = Modifier.padding(padding))
        }
        composable(
            route = Screen.AddExpense.route + "/{expenseId}",
            arguments = listOf(
                navArgument("expenseId") { type = NavType.StringType }
            )
        ) {
            AddExpenseTransaction(
                modifier = Modifier.padding(padding),
                navController = navController
            )
        }
    }
}