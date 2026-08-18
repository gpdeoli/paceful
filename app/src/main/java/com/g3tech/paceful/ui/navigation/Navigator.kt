package com.g3tech.paceful.ui.navigation

interface Navigator<T> {
    val navigationState: NavigationState

    fun navigateTo(destination: T)
    fun goBack()
}
