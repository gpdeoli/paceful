package com.g3tech.paceful.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "study_status", indices = [Index(value = ["name"], unique = true)])
data class StudyStatus(
    @PrimaryKey val id: Int,
    val name: String
)