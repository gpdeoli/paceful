package com.g3tech.paceful.domain.model

import java.time.LocalDate

data class Topic(
    val name: String,
    val deadline: LocalDate? = null,
)
