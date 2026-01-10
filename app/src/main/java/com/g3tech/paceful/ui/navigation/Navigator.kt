package com.g3tech.paceful.ui.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList

interface Navigator<T> {
    val backStack: SnapshotStateList<T>

    fun initializeBackStack(startDestination: T): SnapshotStateList<T>
    fun navigateTo(destination: T)
    fun goBack()
}