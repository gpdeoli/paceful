package com.g3tech.paceful.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator

class NavigationState(
    val startRoute: NavKey,
    topLevelRoute: NavKey,
    val backStacks: Map<NavKey, SnapshotStateList<NavKey>>
) {
    var topLevelRoute: NavKey by mutableStateOf(topLevelRoute)

    val stacksInUse: List<NavKey>
        get() = if (topLevelRoute == startRoute) listOf(startRoute)
                else listOf(startRoute, topLevelRoute)
}

@Composable
fun NavigationState.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>
): List<NavEntry<NavKey>> {
    val homeEntries = rememberDecoratedNavEntries(
        backStack = backStacks[Routes.HomeScreen] ?: emptyList(),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider
    )
    val studyEntries = rememberDecoratedNavEntries(
        backStack = backStacks[Routes.StudyScreen] ?: emptyList(),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider
    )
    val subjectEntries = rememberDecoratedNavEntries(
        backStack = backStacks[Routes.SubjectScreen] ?: emptyList(),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider
    )
    val settingsEntries = rememberDecoratedNavEntries(
        backStack = backStacks[Routes.SettingsScreen] ?: emptyList(),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider
    )

    val allEntries = mapOf(
        Routes.HomeScreen to homeEntries,
        Routes.StudyScreen to studyEntries,
        Routes.SubjectScreen to subjectEntries,
        Routes.SettingsScreen to settingsEntries
    )

    return stacksInUse.flatMap { key -> allEntries[key] ?: emptyList() }
}
