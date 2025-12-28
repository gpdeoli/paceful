package com.g3tech.paceful.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.g3tech.paceful.domain.model.Subject as ModelSubject
import java.time.LocalDate

@Entity
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String?,
    val deadline: LocalDate
) {
    fun toModel(): ModelSubject {
        return ModelSubject(
            name = this.name,
            description = this.description,
            deadline = this.deadline
        )
    }
}

fun ModelSubject.toDbEntity(): Subject {
    return Subject(
        name = this.name,
        description = this.description,
        deadline = this.deadline
    )
}