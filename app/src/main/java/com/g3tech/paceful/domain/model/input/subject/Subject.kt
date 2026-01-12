package com.g3tech.paceful.domain.model.input.subject

import java.time.LocalDate

data class Subject(
    val name: String,
    val deadline: LocalDate,
    val description: String?
)