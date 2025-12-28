package com.g3tech.paceful.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.g3tech.paceful.domain.model.input.CreateStudy
import com.g3tech.paceful.domain.model.Study as ModelStudy
import com.g3tech.paceful.domain.model.StudyStatus as ModelStudyStatus
import java.time.LocalDate

@Entity(
    indices = [Index(value = ["status"]), Index(value = ["subject"])],
    tableName = "study",
    foreignKeys = [ForeignKey(
        entity = StudyStatus::class,
        parentColumns = ["id"],
        childColumns = ["status"]
    ), ForeignKey(
        entity = Subject::class,
        parentColumns = ["id"],
        childColumns = ["subject"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Study(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val status: Long,
    val subject: Long?,
    val deadline: LocalDate,
    @ColumnInfo(name = "start_date") val startDate: LocalDate = LocalDate.now()
) {
    fun toModel() : ModelStudy {
        return ModelStudy(
            name = this.name,
            status = ModelStudyStatus.entries.find { it.id == this.status } ?: ModelStudyStatus.PENDING,
            deadline = deadline,
            startDate = startDate
        )
    }
}

fun CreateStudy.toDbEntity(): Study {
    return Study(
        name = this.name,
        status = this.status,
        subject = this.subject,
        deadline = this.deadline,
        startDate = this.startDate
    )
}