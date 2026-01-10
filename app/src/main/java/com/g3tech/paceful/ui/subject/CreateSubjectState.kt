package com.g3tech.paceful.ui.subject

import java.time.LocalDate

data class CreateSubjectState(
    val name: String = "",
    val description: String = "",
    val deadline: LocalDate? = null,
    val canSave: Boolean = false,
    val isLoading: Boolean = false,
    val saveFailed: Boolean = false
)
