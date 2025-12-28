package com.g3tech.paceful.ui.home

sealed interface HomeScreenEvent {
    data class ViewAllClick(val subjectId: Long?) : HomeScreenEvent
    data object NewSubjectClick : HomeScreenEvent
    data object NewStudyClick : HomeScreenEvent
}
