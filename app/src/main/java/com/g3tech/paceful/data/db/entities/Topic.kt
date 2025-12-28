package com.g3tech.paceful.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.g3tech.paceful.domain.model.input.CreateTopic
import com.g3tech.paceful.domain.model.Topic as ModelTopic
import java.time.LocalDate

@Entity(
    indices = [
        Index(value = ["study_id"])
    ],
    tableName = "topic",
    foreignKeys = [
        ForeignKey(
            entity = Study::class,
            parentColumns = ["id"],
            childColumns = ["study_id"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "study_id") val studyId: Long,
    val deadline: LocalDate? = null,
) {
    fun toModel(): ModelTopic {
        return ModelTopic(
            name = this.name,
            deadline = this.deadline,
        )
    }
}

fun CreateTopic.toDbEntity(): Topic {
    return Topic(
        name = this.name,
        deadline = this.deadline,
        studyId = this.studyId
    )
}