package com.g3tech.paceful.domain.model

import java.time.LocalDate
import com.g3tech.paceful.data.db.entities.Topic as TopicDbEntity

data class Topic(
    val name: String,
    val deadline: LocalDate? = null
) {
    fun toDbEntity(): TopicDbEntity {
        return TopicDbEntity(name = name, deadline = deadline, studyId = 0)
    }
}
