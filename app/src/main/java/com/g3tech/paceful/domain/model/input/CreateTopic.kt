package com.g3tech.paceful.domain.model.input

import java.time.LocalDate

data class CreateTopic(
    val name: String,
    val deadline: LocalDate? = null,
)