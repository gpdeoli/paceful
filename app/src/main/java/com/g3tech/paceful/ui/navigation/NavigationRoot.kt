package com.g3tech.paceful.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.g3tech.paceful.ui.home.HomeScreen

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Routes.HomeScreen)
    NavDisplay(
        backStack = backStack, entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = { key ->
            when (key) {
                is Routes.HomeScreen -> NavEntry(key) { HomeScreen() }
                else -> throw RuntimeException("Invalid key")
            }
        })
}