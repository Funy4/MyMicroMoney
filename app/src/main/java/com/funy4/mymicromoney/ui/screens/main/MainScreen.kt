package com.funy4.mymicromoney.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.funy4.mymicromoney.ui.navigation.BottomBar
import com.funy4.mymicromoney.ui.navigation.NavGraph
import com.funy4.mymicromoney.ui.screens.expensescreen.views.TopBar
import com.funy4.mymicromoney.ui.screens.main.viewmodel.MainEvent
import com.funy4.mymicromoney.ui.screens.main.viewmodel.MainUiEvent
import com.funy4.mymicromoney.ui.screens.main.viewmodel.MainViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressLint("NewApi")
@ExperimentalAnimationApi
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is MainUiEvent.Navigate -> {
                    navigateToRoute(navController, event.route, currentDestination?.route)
                }
            }
        }
    }

    Scaffold(
        topBar = { TopBar(money = viewModel.allCosts.value)},
        bottomBar = {
            BottomBar(
                navController = navController,
                onRouteClick = {
                    viewModel.onEvent(MainEvent.OnNavigateScreenClick(it))
                }
            )
        }) { padding ->
        NavGraph(navController = navController, padding)
    }
}

private fun navigateToRoute(
    navController: NavHostController,
    route: String,
    currentDestination: String?
) {
    navController.navigate(route) {
//        /** Дабы при выборе текущего экрана ничего не происходило **/
//        launchSingleTop = true
//        /** Чтобы возвращаться было некуда и мы просто выходили к чёрту **/
//        if (currentDestination != route) {
//            currentDestination?.let {
//                popUpTo(it) {
//                    inclusive = true
//                }
//            }
//        }
        /** From Google **/
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}
