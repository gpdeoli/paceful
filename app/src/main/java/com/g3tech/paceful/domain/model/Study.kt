package com.g3tech.paceful.domain.model

import com.g3tech.paceful.data.db.entities.Study as StudyDbEntity
import java.util.Date

data class Study(
    val subject: String,
    val status: StudyStatus,
    val topics: List<Topic>?,
    val deadline: Date,
    val startDate: Date = Date()
) {
    fun toDbEntity(): StudyDbEntity {
        return StudyDbEntity(subject = subject, status = status.id, deadline = deadline, startDate = startDate)
    }
}
