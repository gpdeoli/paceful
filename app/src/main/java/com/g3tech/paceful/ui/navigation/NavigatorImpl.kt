package com.g3tech.paceful.ui.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

class NavigatorImpl: Navigator<NavKey> {
    override val backStack: SnapshotStateList<NavKey> = mutableStateListOf()

    override fun initializeBackStack(startDestination: NavKey): SnapshotStateList<NavKey> {
        backStack.clear()
        backStack.add(startDestination)
        return backStack
    }

    override fun navigateTo(destination: NavKey) {
        backStack.add(destination)
    }

    override fun goBack() {
        backStack.removeLastOrNull()
    }
}