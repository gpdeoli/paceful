package com.g3tech.paceful.ui.study.createstudy

import com.g3tech.paceful.domain.model.input.CreateTopic
import com.g3tech.paceful.domain.model.input.subject.SubjectToSelect
import java.time.LocalDate

data class CreateStudyState(
    val name: String = "",
    val deadline: LocalDate? = null,
    val startDate: LocalDate = LocalDate.now(),
    val selectedSubject: SubjectToSelect? = null,

    val currentTopic: CreateTopic = CreateTopic(name = "", null),
    val subjectOptions: List<SubjectToSelect> = emptyList(),
    val topics: List<CreateTopic>? = null,

    val canAddTopic: Boolean = false,
    val canSave: Boolean = false,
    val isLoading: Boolean = false,
    val saveFailed: Boolean = false
)
