package com.g3tech.paceful.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.g3tech.paceful.ui.home.HomeScreen
import com.g3tech.paceful.ui.home.HomeViewModel
import com.g3tech.paceful.ui.shared.bottomappbar.BottomAppBar
import com.g3tech.paceful.ui.study.createstudy.CreateStudyScreen
import com.g3tech.paceful.ui.study.createstudy.CreateStudyViewModel
import com.g3tech.paceful.ui.subject.CreateSubjectScreen
import com.g3tech.paceful.ui.subject.CreateSubjectViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun NavigationRoot() {
    val navigator: Navigator<NavKey> = koinInject()
    val navState = navigator.navigationState

    val entries = navState.toEntries { key ->
        when (key) {
            is Routes.HomeScreen -> NavEntry(key) {
                val viewModel: HomeViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                HomeScreen(state, viewModel::onEvent)
            }

            is Routes.StudyScreen -> NavEntry(key) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Studies")
                }
            }

            is Routes.SubjectScreen -> NavEntry(key) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Subjects")
                }
            }

            is Routes.SettingsScreen -> NavEntry(key) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Settings")
                }
            }

            is Routes.CreateSubjectScreen -> NavEntry(key) {
                val viewModel: CreateSubjectViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                CreateSubjectScreen(state = state, onEvent = viewModel::onEvent)
            }

            is Routes.CreateStudyScreen -> NavEntry(key) {
                val viewModel: CreateStudyViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                CreateStudyScreen(state, viewModel::onEvent)
            }

            else -> NavEntry(key) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Page not found. Key: $key")
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                selectedKey = navState.topLevelRoute,
                onKeySelected = { navigator.navigateTo(it) }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            entries = entries,
            onBack = { navigator.goBack() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
