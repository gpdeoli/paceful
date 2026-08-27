package com.g3tech.paceful.ui.study.studies

import com.g3tech.paceful.domain.model.StudyStatus
import java.time.LocalDate

sealed interface StudiesEvent {
    data class SearchQueryChanged(val query: String) : StudiesEvent
    // null = "All" (clears status selection)
    data class StatusToggled(val status: StudyStatus?) : StudiesEvent
    data class ApplyFilters(
        val searchQuery: String,
        val statuses: Set<StudyStatus>,
        val subjectId: Long?,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
    ) : StudiesEvent
    data object ToggleFilterSheet : StudiesEvent
    data object LoadMore : StudiesEvent
    data object NewStudyClick : StudiesEvent
}
