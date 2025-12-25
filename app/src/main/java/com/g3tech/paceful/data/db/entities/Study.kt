package com.g3tech.paceful.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    indices = [Index(value = ["status"])],
    tableName = "study",
    foreignKeys = [ForeignKey(
        entity = StudyStatus::class,
        parentColumns = ["id"],
        childColumns = ["status"]
    )]
)
data class Study(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val status: Int,
    val deadline: LocalDate,
    @ColumnInfo(name = "start_date") val startDate: LocalDate = LocalDate.now()
)