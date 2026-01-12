package com.g3tech.paceful.ui.home

import com.g3tech.paceful.domain.model.StudiesSummary

data class HomeScreenState(
    val summaryData: StudiesSummary = StudiesSummary(
        studiesSummaryNumbers = emptyList(),
        subjects = emptyList(),
        urgentStudies = emptyList(),
        overdueStudies = emptyList(),
        studiesWithSubjects = emptyList(),
    ),
    val isLoading: Boolean = false,
)
