package com.funy4.mymicromoney.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomBar(navController: NavHostController, onRouteClick: (String) -> Unit) {

    val screens = listOf(
        BottomNavBarScreens.Cash,
        BottomNavBarScreens.Expenses,
        BottomNavBarScreens.Income,
        BottomNavBarScreens.Ai
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation {
        screens.forEach {
            AddItem(
                screen = it,
                currentDestination = currentDestination,
                onRouteClick = onRouteClick
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavBarScreens,
    currentDestination: NavDestination?,
    onRouteClick: (String) -> Unit
) {
    BottomNavigationItem(
        alwaysShowLabel = false,
        label = { Text(text = screen.title) },
        icon = {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = ImageVector.vectorResource(id = screen.iconId),
                contentDescription = null
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            onRouteClick(screen.route)
        },
    )

}