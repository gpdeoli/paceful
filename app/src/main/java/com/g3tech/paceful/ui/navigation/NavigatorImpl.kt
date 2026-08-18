package com.g3tech.paceful.ui.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey

class NavigatorImpl : Navigator<NavKey> {

    private val tabRoutes: Set<NavKey> = setOf(
        Routes.HomeScreen,
        Routes.StudyScreen,
        Routes.SubjectScreen,
        Routes.SettingsScreen
    )

    override val navigationState: NavigationState = NavigationState(
        startRoute = Routes.HomeScreen,
        topLevelRoute = Routes.HomeScreen,
        backStacks = tabRoutes.associateWith { route -> mutableStateListOf(route) }
    )

    override fun navigateTo(destination: NavKey) {
        if (destination in tabRoutes) {
            if (destination == navigationState.topLevelRoute) {
                navigationState.backStacks[destination]?.let { stack ->
                    while (stack.size > 1) stack.removeLastOrNull()
                }
            } else {
                navigationState.topLevelRoute = destination
            }
        } else {
            navigationState.backStacks[navigationState.topLevelRoute]?.add(destination)
        }
    }

    override fun goBack() {
        val state = navigationState
        val currentStack = state.backStacks[state.topLevelRoute] ?: return
        when {
            currentStack.size > 1 -> currentStack.removeLastOrNull()
            state.topLevelRoute != state.startRoute -> state.topLevelRoute = state.startRoute
        }
    }
}
