package com.g3tech.paceful.data.db.entities

import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.StudiesSummaryNumbers as ModelStudiesSummaryNumbers
data class StudiesSummaryNumbers(
    val value: Int,
    val status: Long
) {
    fun toModel(): ModelStudiesSummaryNumbers {
        return ModelStudiesSummaryNumbers(
            value = value,
            status = StudyStatus.entries.find { it.id == status } ?: StudyStatus.PENDING
        )
    }
}
