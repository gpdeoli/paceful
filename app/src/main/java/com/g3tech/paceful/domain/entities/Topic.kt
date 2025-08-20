package com.g3tech.paceful.domain.entities

import com.g3tech.paceful.db.entities.Topic as TopicDbEntity
import java.util.Date

data class Topic(
    val name: String,
    val deadline: Date? = null
) {
    fun toDbEntity(): TopicDbEntity {
        return TopicDbEntity(name = name, deadline = deadline, studyId = 0)
    }
}
