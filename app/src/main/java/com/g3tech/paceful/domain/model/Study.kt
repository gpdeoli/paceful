package com.g3tech.paceful.domain.model

import java.time.LocalDate
import com.g3tech.paceful.data.db.entities.Study as StudyDbEntity

data class Study(
    val subject: String,
    val status: StudyStatus,
    val topics: List<Topic>?,
    val deadline: LocalDate,
    val startDate: LocalDate = LocalDate.now()
) {
    fun toDbEntity(): StudyDbEntity {
        return StudyDbEntity(subject = subject, status = status.id, deadline = deadline, startDate = startDate)
    }
}
