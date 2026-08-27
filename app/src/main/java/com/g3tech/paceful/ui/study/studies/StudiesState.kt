package com.g3tech.paceful.ui.study.studies

import com.g3tech.paceful.domain.model.StudySummary
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.input.subject.SubjectToSelect
import java.time.LocalDate

data class StudiesState(
    val studies: List<StudySummary> = emptyList(),
    val subjectOptions: List<SubjectToSelect> = emptyList(),
    val searchQueryFilter: String = "",
    val startDateFilter: LocalDate? = null,
    val endDateFilter: LocalDate? = null,
    val studyStatusFilter: Set<StudyStatus> = emptySet(),
    val subjectIdFilter: Long? = null,
    val isFilterSheetVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasMore: Boolean = true,
    val currentPage: Int = 0,
) {
    val hasActiveFilters: Boolean
        get() = searchQueryFilter.isNotBlank()
             || studyStatusFilter.isNotEmpty()
             || subjectIdFilter != null
             || startDateFilter != null
             || endDateFilter != null
}
