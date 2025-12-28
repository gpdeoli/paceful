package com.g3tech.paceful.domain.model

data class StudyDetails(
    val study: Study,
    val subject: Subject?,
    val topics: List<Topic>?
)
