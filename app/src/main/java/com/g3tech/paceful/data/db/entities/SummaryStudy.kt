package com.g3tech.paceful.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.g3tech.paceful.domain.model.StudySummary

data class SummaryStudy(
    @Embedded val study: Study,
    @Relation(parentColumn = "id", entityColumn = "study_id") val topics: List<Topic>?,
) {
    fun toModel(): StudySummary {
        return StudySummary(
            study = study.toModel(),
            topics = topics?.map { it.toModel() },
            subject = study.subject
        )
    }
}
