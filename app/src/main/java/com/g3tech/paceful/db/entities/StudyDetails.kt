package com.g3tech.paceful.db.entities

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
        entityColumn = "studyId"
    ) val topics: List<Topic>
) {
    fun
}