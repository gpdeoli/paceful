package com.g3tech.paceful.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.g3tech.paceful.data.db.entities.Subject
import com.g3tech.paceful.domain.model.input.subject.PartialSubject
import com.g3tech.paceful.domain.model.input.subject.SubjectToSelect

@Dao
interface SubjectDao {
    @Insert
    suspend fun insertSubject(subject: Subject)

    @Query(
        """
        SELECT subject.id, subject.name, subject.description
        FROM subject
        INNER JOIN study ON subject.id = study.subject
        LIMIT 3
        """
    )
    suspend fun getSummarySubjects(): List<PartialSubject>

    @Query("""SELECT id, name FROM subject WHERE deadline < DATE('NOW()')""")
    suspend fun getSubjectsToSelect(): List<SubjectToSelect>
}