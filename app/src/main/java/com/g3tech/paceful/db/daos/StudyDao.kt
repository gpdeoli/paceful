package com.g3tech.paceful.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.g3tech.paceful.db.entities.Study
import com.g3tech.paceful.db.entities.StudyDetails
import com.g3tech.paceful.db.entities.Topic

@Dao
interface StudyDao {
    @Insert
    suspend fun insertStudy(study: Study): Int

    @Insert
    suspend fun insertTopics(topics: List<Topic>)

    @Transaction
    suspend fun insertStudyWithTopics(study: Study, topics: List<Topic>) {
        val studyId = insertStudy(study)
        topics.forEach { topic -> topic.studyId = studyId }
        insertTopics(topics)
    }

    @Transaction
    @Query("SELECT * FROM study WHERE id IN (SELECT * FROM study_status WHERE name = 'Pending')")
    suspend fun getPendingStudies(): List<StudyDetails>

    @Transaction
    @Query("SELECT * FROM study WHERE id IN (SELECT * FROM study_status WHERE name = 'In Progress')")
    suspend fun getInProgressStudies(): List<StudyDetails>

    @Transaction
    @Query("SELECT * FROM study WHERE id IN (SELECT * FROM study_status WHERE name = 'Done')")
    suspend fun getDoneStudies(): List<StudyDetails>

    @Transaction
    @Query("SELECT * FROM study WHERE id IN (SELECT * FROM study_status WHERE name = 'Scheduled')")
    suspend fun getScheduledStudies(): List<StudyDetails>
}
