package com.g3tech.paceful.ui.navigation

import androidx.navigation3.runtime.NavKey
import com.g3tech.paceful.domain.model.StudyStatus
import kotlinx.serialization.Serializable
sealed interface Routes : NavKey {
    @Serializable
    data object HomeScreen : Routes

    @Serializable
    data object CreateSubjectScreen : Routes

    @Serializable
    data object CreateStudyScreen : Routes

    @Serializable
    data class StudyScreen(
        val subjectId: Long? = null,
        val status: Set<StudyStatus>? = null,
        val startDate: String? = null,
        val endDate: String? = null,
    ) : Routes

    @Serializable
    data object SubjectScreen : Routes

    @Serializable
    data object SettingsScreen : Routes
}