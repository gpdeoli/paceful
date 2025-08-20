package com.g3tech.paceful.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.g3tech.paceful.ui.theme.home.HomeScreen
import com.g3tech.paceful.ui.theme.home.HomeViewModel

@Composable
fun NavController(navHostController: NavHostController = rememberNavController(), navigator: Navigator) {
    val navigatorState by navigator.actions.collectAsStateWithLifecycle()

    LaunchedEffect(navigatorState) {
        navigatorState?.let { navigationAction ->
            when (navigationAction.destination) {
                is Routes.PopBack -> {
                    navHostController.popBackStack()
                }

                else -> {
                    navigationAction.parcelableArguments.forEach { arg ->
                        navHostController.currentBackStackEntry?.arguments?.putParcelable(
                            arg.key, arg.value
                        )
                    }
                    navHostController.navigate(
                        navigationAction.destination,
                        navigationAction.navOptions
                    )
                }
            }
        }
    }

    NavHost(navController = navHostController, startDestination = Routes.HomeScreenRoute) {
        composable<Routes.HomeScreenRoute> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen()
        }

    }
}