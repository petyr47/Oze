package com.aneke.peter.oze.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.ExperimentalPagingApi
import com.aneke.peter.oze.data.Result
import com.aneke.peter.oze.main.DetailLayout
import com.aneke.peter.oze.main.FavoriteList
import com.aneke.peter.oze.main.MainViewModel
import com.aneke.peter.oze.main.ResultList
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun NavigationGraph(navController: NavHostController, mainViewModel: MainViewModel) {

    NavHost(navController, startDestination = BottomNavItem.ResultList.screenRoute) {
        composable(BottomNavItem.ResultList.screenRoute) {
            ResultList(mainViewModel, navController)
        }
        composable(BottomNavItem.FavoriteList.screenRoute) {
            FavoriteList(mainViewModel, navController)
        }
        composable("detail") {
            // Directly extract the argument from previousBackStackEntry
            val result = navController.previousBackStackEntry?.arguments?.getParcelable<Result>("result")
            DetailLayout(mainViewModel,navController, result)
        }
    }
}



@Composable
fun BottomNavigator(navController: NavController) {

    val items = listOf(
        BottomNavItem.ResultList,
        BottomNavItem.FavoriteList,
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 14.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
