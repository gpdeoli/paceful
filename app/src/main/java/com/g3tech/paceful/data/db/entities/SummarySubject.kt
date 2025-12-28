package com.g3tech.paceful.data.db.entities

import com.g3tech.paceful.domain.model.PartialSubject

data class SummarySubject(
    val id: Long,
    val name: String,
    val description: String?,
) {
    fun toModel(): PartialSubject {
        return PartialSubject(
            id = id,
            name = name,
            description = description
        )
    }
}