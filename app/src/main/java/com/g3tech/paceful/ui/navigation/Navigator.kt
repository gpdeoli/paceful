package com.g3tech.paceful.ui.navigation

import kotlinx.coroutines.flow.StateFlow

interface Navigator {
    val actions: StateFlow<NavigationAction?>
    fun navigate(action: NavigationAction)
}