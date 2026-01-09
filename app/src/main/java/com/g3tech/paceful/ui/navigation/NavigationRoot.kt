package com.g3tech.paceful.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.g3tech.paceful.ui.home.HomeScreen
import com.g3tech.paceful.ui.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

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
                is Routes.HomeScreen -> NavEntry(key) {
                    val viewModel: HomeViewModel = koinViewModel()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    HomeScreen(state, viewModel::onEvent)
                }
                else -> NavEntry(key) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Page not found. Key: $key")
                    }
                }
            }
        })
}