package com.g3tech.paceful.routing

import kotlinx.coroutines.flow.StateFlow

interface Navigator {
    val actions: StateFlow<NavigationAction?>
    fun navigate(action: NavigationAction)
}