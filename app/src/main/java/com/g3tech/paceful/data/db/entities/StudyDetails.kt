package com.g3tech.paceful.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class StudyDetails(
    @Embedded val study: Study,

    @Relation(
        parentColumn = "status",
        entityColumn = "id"
    ) val status: StudyStatus,

    @Relation(
        parentColumn = "id",
        entityColumn = "study_id"
    ) val topics: List<Topic>
)