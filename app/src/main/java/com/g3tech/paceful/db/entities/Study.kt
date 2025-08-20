package com.g3tech.paceful.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.g3tech.paceful.db.entities.StudyStatus
import java.util.Date

@Entity(
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
    val deadline: Date,
    @ColumnInfo(name = "start_date") val startDate: Date = Date()
)