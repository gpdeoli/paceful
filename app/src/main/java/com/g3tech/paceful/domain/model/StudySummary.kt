package com.g3tech.paceful.domain.model

data class StudySummary(
    val study: Study,
    val topics: List<Topic>?,
    val subject: Long?
)
