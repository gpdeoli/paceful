package com.g3tech.paceful.domain.model.input

import com.g3tech.paceful.domain.model.StudyStatus
import java.time.LocalDate

data class GetStudiesQueryParams(
    val searchQuery: String? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val statuses: Set<StudyStatus> = emptySet(),
    val subjectId: Long? = null,
    val page: Int = 0,
    val pageSize: Int = 20,
)
