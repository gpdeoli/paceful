package com.g3tech.paceful.domain.model

import com.g3tech.paceful.data.db.entities.StudyDetails

data class StudySummary(
    val pendingStudies: List<StudyDetails>,
    val inProgressStudies: List<StudyDetails>,
    val doneStudies: List<StudyDetails>,
    val scheduledStudies: List<StudyDetails>
)