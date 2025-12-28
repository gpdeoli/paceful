package com.g3tech.paceful.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.g3tech.paceful.domain.model.StudyDetails as ModelStudyDetails

data class StudyDetails(
    @Embedded val study: Study,

    @Relation(
        parentColumn = "subject",
        entityColumn = "id"
    ) val subject: Subject?,

    @Relation(
        parentColumn = "status",
        entityColumn = "id"
    ) val status: StudyStatus,

    @Relation(
        parentColumn = "id",
        entityColumn = "study_id"
    ) val topics: List<Topic>?
) {
    fun toModel(): ModelStudyDetails {
        return ModelStudyDetails(
            study = this.study.toModel(),
            subject = this.subject?.toModel(),
            topics = this.topics?.map { it.toModel() }
        )
    }
}