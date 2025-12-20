package com.g3tech.paceful.domain.model

import com.g3tech.paceful.data.db.entities.Topic as TopicDbEntity
import java.util.Date

data class Topic(
    val name: String,
    val deadline: Date? = null
) {
    fun toDbEntity(): TopicDbEntity {
        return TopicDbEntity(name = name, deadline = deadline, studyId = 0)
    }
}
