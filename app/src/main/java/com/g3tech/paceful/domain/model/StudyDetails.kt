package com.g3tech.paceful.domain.model

import com.g3tech.paceful.domain.model.input.subject.Subject

data class StudyDetails(
    val study: Study,
    val subject: Subject?,
    val topics: List<Topic>?
)
