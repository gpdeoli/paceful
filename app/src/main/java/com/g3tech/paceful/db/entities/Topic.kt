package com.g3tech.paceful.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "topic",
    foreignKeys = [
        ForeignKey(
            entity = Study::class,
            parentColumns = ["id"],
            childColumns = ["studyId"],
            onDelete = ForeignKey.Companion.CASCADE
        )]
)
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "study_id") var studyId: Int,
    val deadline: Date? = null,
)