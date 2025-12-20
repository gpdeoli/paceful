package com.g3tech.paceful.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import com.g3tech.paceful.data.db.entities.StudyStatus

@Dao
interface StudyStatusDao {
    @Insert
    fun insertAll(studyStatuses: List<StudyStatus>)
}