package com.g3tech.paceful.ui.home

import com.g3tech.paceful.domain.model.PartialSubject
import com.g3tech.paceful.domain.model.input.CreateTopic
import java.time.LocalDate

sealed interface HomeScreenEvent {
    data class ViewAllClick(val subjectId: Long?) : HomeScreenEvent
    data object NewSubjectClick : HomeScreenEvent
    data object NewStudyClick : HomeScreenEvent
    data class ToggleCreateStudyDialog(val isOpen: Boolean) : HomeScreenEvent
}

sealed interface CreateStudyEvent: HomeScreenEvent {
    data class DeadlineChanged(val selectedDate: LocalDate): CreateStudyEvent
    data class StartDateChanged(val selectedDate: LocalDate): CreateStudyEvent
    data class NameChanged(val name: String): CreateStudyEvent
    data class SubjectChanged(val subject: PartialSubject?): CreateStudyEvent
    data class TopicNameChanged(val topicName: String): CreateStudyEvent
    data class TopicDeadlineChanged(val topicDeadline: LocalDate): CreateStudyEvent
    data object AddTopic: CreateStudyEvent
    data class RemoveTopic(val topic: CreateTopic): CreateStudyEvent
    data object SaveFailedMessageShown: CreateStudyEvent
    data object Save: CreateStudyEvent
}
