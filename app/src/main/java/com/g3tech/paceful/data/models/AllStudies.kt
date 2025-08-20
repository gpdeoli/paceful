package com.g3tech.paceful.data.models

import com.g3tech.paceful.db.entities.StudyDetails

data class AllStudies(
    val pendingStudies: List<StudyDetails>,
    val inProgressStudies: List<StudyDetails>,
    val doneStudies: List<StudyDetails>,
    val scheduledStudies: List<StudyDetails>
)
