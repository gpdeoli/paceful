package com.g3tech.paceful.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.g3tech.paceful.data.db.entities.StudiesSummaryNumbers
import com.g3tech.paceful.data.db.entities.Study
import com.g3tech.paceful.data.db.entities.SummaryStudy
import com.g3tech.paceful.data.db.entities.Topic
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

@Dao
interface StudyDao {
    @Insert
    suspend fun insertStudy(study: Study): Long

    @Insert
    suspend fun insertTopics(topics: List<Topic>)

    @Transaction
    suspend fun insertStudyWithTopics(study: Study, topics: List<Topic>) {
        val studyId = insertStudy(study)
        val linkedStudies = topics.map { topic -> topic.copy(studyId = studyId) }
        insertTopics(linkedStudies)
    }

    @Transaction
    @Query(
        """
        SELECT * FROM study
        WHERE deadline BETWEEN DATE('now') AND DATE('now', '+7 days') 
        AND status IN (SELECT id FROM study_status WHERE name != 'DONE')
        """
    )
    suspend fun getUrgentStudies(): List<SummaryStudy>

    @Transaction
    @Query("SELECT * FROM study WHERE deadline < DATE('now') AND status IN (SELECT id FROM study_status WHERE name != 'DONE')")
    suspend fun getOverdueStudies(): List<SummaryStudy>

    @Transaction
    suspend fun getStudiesWithSubjects(subjectsIds: List<Long>): List<SummaryStudy> =
        coroutineScope {
            val defferedStudies = subjectsIds.map { subjectsId ->
                async {
                    getStudyWithSubject(subjectsId)
                }
            }
            defferedStudies.awaitAll().flatten()
        }

    @Query(
        """
        SELECT * FROM study WHERE subject = :subjectId LIMIT 5
    """
    )
    suspend fun getStudyWithSubject(subjectId: Long): List<SummaryStudy>

    @Query("""SELECT * FROM study WHERE subject IS NULL LIMIT 5""")
    suspend fun getStudiesWithoutSubjects(): List<SummaryStudy>

    @Query(
        """
        SELECT COUNT(*) as value, status FROM study
        GROUP BY status
    """
    )
    suspend fun getStudiesSummaryNumbers(): List<StudiesSummaryNumbers>
}
