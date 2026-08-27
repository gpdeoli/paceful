package com.g3tech.paceful.ui.home

import com.g3tech.paceful.domain.model.StudyStatus
import java.time.LocalDate

sealed interface HomeScreenEvent {
    data class ViewAllClick(
        val subjectId: Long? = null,
        val status: Set<StudyStatus>? = null,
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null,
    ) : HomeScreenEvent
    data object NewSubjectClick : HomeScreenEvent
    data object NewStudyClick : HomeScreenEvent
}
