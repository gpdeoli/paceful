package com.g3tech.paceful.ui.subject

import java.time.LocalDate

sealed interface CreateSubjectEvent {
    data class NameChanged(val name: String): CreateSubjectEvent
    data class DeadlineChanged(val selectedDate: LocalDate): CreateSubjectEvent
    data class DescriptionChanged(val description: String): CreateSubjectEvent
    data object Save: CreateSubjectEvent
    data object SaveFailedMessageShown: CreateSubjectEvent
    data object GoBack: CreateSubjectEvent
}