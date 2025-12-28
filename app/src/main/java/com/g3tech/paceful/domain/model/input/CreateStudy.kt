package com.g3tech.paceful.domain.model.input

import java.time.LocalDate

data class CreateStudy(
    val subject: Long?,
    val name: String,
    val status: Long,
    val topics: List<CreateTopic>?,
    val deadline: LocalDate,
    val startDate: LocalDate = LocalDate.now()
)