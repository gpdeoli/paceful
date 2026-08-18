package com.g3tech.paceful.ui.shared.bottomappbar

import com.g3tech.paceful.R
import com.g3tech.paceful.ui.navigation.Routes

data class BottomAppItem(
    val iconResource: Int,
    val labelResource: Int,
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Routes.HomeScreen to BottomAppItem(
        R.drawable.home_24,
        R.string.home
    ),
    Routes.StudyScreen to BottomAppItem(
        R.drawable.study_24,
        R.string.studies,
    ),
    Routes.SubjectScreen to BottomAppItem(
        R.drawable.subject_24,
        R.string.subjects
    ),
    Routes.SettingsScreen to BottomAppItem(
        R.drawable.settings_24,
        R.string.settings
    )
)
