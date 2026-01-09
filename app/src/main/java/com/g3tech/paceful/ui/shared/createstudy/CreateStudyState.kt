package com.g3tech.paceful.ui.shared.createstudy

import com.g3tech.paceful.domain.model.PartialSubject
import com.g3tech.paceful.domain.model.input.CreateTopic
import java.time.LocalDate

data class CreateStudyState(
    val subjects: List<PartialSubject> = emptyList(),
    val name: String = "",
    val selectedSubject: PartialSubject? = null,
    val deadline: LocalDate? = null,
    val startDate: LocalDate = LocalDate.now(),
    val topics: List<CreateTopic>? = null,
    val currentTopic: CreateTopic = CreateTopic(name = "", null),
    val canAddTopic: Boolean = false,
    val canSave: Boolean = false,
    val isLoading: Boolean = false,
    val saveFailed: Boolean = false
)
