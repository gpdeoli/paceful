package com.g3tech.paceful.domain.model

data class StudiesSummary(
    val studiesSummaryNumbers: List<StudiesSummaryNumbers>,
    val subjects: List<PartialSubject>,
    val urgentStudies: List<StudySummary>,
    val overdueStudies: List<StudySummary>,
    val studiesWithSubjects: List<StudySummary>,
)