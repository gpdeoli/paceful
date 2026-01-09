package com.g3tech.paceful.ui.home

import com.g3tech.paceful.domain.model.StudiesSummary
import com.g3tech.paceful.ui.shared.createstudy.CreateStudyState

data class HomeScreenState(
    val summaryData: StudiesSummary = StudiesSummary(
        studiesSummaryNumbers = emptyList(),
        subjects = emptyList(),
        urgentStudies = emptyList(),
        overdueStudies = emptyList(),
        studiesWithSubjects = emptyList(),
    ),
    val createStudyOpen: Boolean = false,
    val createStudyState: CreateStudyState = CreateStudyState(),
    val isLoading: Boolean = false,
)
