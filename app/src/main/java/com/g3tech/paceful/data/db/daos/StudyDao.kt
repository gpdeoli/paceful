package com.g3tech.paceful.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.g3tech.paceful.data.db.entities.Study
import com.g3tech.paceful.data.db.entities.StudyDetails
import com.g3tech.paceful.data.db.entities.Topic

@Dao
interface StudyDao {
    @Insert
    suspend fun insertStudy(study: Study) : Long

    @Insert
    suspend fun insertTopics(topics: List<Topic>)

    @Transaction
    suspend fun insertStudyWithTopics(study: Study, topics: List<Topic>) {
        val studyId = insertStudy(study)
        topics.forEach { topic -> topic.studyId = studyId }
        insertTopics(topics)
    }

    @Transaction
    @Query("SELECT * FROM study WHERE status = (SELECT id FROM study_status WHERE name = 'PENDING')")
    suspend fun getPendingStudies(): List<StudyDetails>

    @Transaction
    @Query("SELECT * FROM study WHERE status = (SELECT id FROM study_status WHERE name = 'IN PROGRESS')")
    suspend fun getInProgressStudies(): List<StudyDetails>

    @Transaction
    @Query("SELECT * FROM study WHERE status = (SELECT id FROM study_status WHERE name = 'DONE')")
    suspend fun getDoneStudies(): List<StudyDetails>

    @Transaction
    @Query("SELECT * FROM study WHERE status = (SELECT id FROM study_status WHERE name = 'SCHEDULED')")
    suspend fun getScheduledStudies(): List<StudyDetails>
}
