package com.g3tech.paceful.domain.model

import com.g3tech.paceful.domain.model.input.subject.PartialSubject

data class StudySummary(
    val study: Study,
    val topics: List<Topic>? = null,
    val subject: PartialSubject? = null
)
