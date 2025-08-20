package com.g3tech.paceful.db.daos

import androidx.room.Insert
import com.g3tech.paceful.db.entities.StudyStatus

interface StudyStatusDao {
    @Insert
    fun insertAll(studyStatuses: List<StudyStatus>): Int
}