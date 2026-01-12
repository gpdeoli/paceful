package com.g3tech.paceful.domain.model

import com.g3tech.paceful.domain.model.input.subject.PartialSubject

data class StudiesSummary(
    val studiesSummaryNumbers: List<StudiesSummaryNumbers>,
    val subjects: List<PartialSubject>,
    val urgentStudies: List<StudySummary>,
    val overdueStudies: List<StudySummary>,
    val studiesWithSubjects: List<StudySummary>,
    val studiesWithoutSubjects: List<StudySummary>? = null
)