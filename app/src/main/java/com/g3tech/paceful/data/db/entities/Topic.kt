package com.g3tech.paceful.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
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
    @ColumnInfo(name = "study_id") var studyId: Long,
    val deadline: LocalDate? = null,
)