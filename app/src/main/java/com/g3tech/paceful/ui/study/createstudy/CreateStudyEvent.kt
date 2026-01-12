package com.g3tech.paceful.ui.study.createstudy

import com.g3tech.paceful.domain.model.input.CreateTopic
import com.g3tech.paceful.domain.model.input.subject.SubjectToSelect
import java.time.LocalDate

sealed interface CreateStudyEvent {
    data class NameChanged(val name: String): CreateStudyEvent
    data class DeadlineChanged(val selectedDate: LocalDate): CreateStudyEvent
    data class StartDateChanged(val selectedDate: LocalDate): CreateStudyEvent
    data class SubjectChanged(val subject: SubjectToSelect?): CreateStudyEvent
    data class TopicNameChanged(val topicName: String): CreateStudyEvent
    data class TopicDeadlineChanged(val topicDeadline: LocalDate): CreateStudyEvent
    data object AddTopic: CreateStudyEvent
    data class RemoveTopic(val topic: CreateTopic): CreateStudyEvent
    data object SaveFailedMessageShown: CreateStudyEvent
    data object Save: CreateStudyEvent
    data object GoBack: CreateStudyEvent
}