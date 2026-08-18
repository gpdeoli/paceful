package com.g3tech.paceful.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Routes : NavKey {
    @Serializable
    data object HomeScreen : Routes

    @Serializable
    data object CreateSubjectScreen: Routes

    @Serializable
    data object CreateStudyScreen: Routes

    @Serializable
    data object StudyScreen: Routes

    @Serializable
    data object SubjectScreen: Routes

    @Serializable
    data object SettingsScreen: Routes
}