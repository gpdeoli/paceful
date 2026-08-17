package com.g3tech.paceful.domain.model.input.subject

data class SubjectToSelect(
    val id: Long,
    val name: String,
    val deadline: String?,
    val isDueDateNear: Boolean,
)
