package com.g3tech.paceful.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.g3tech.paceful.data.db.entities.Subject
import com.g3tech.paceful.data.db.entities.SummarySubject

@Dao
interface SubjectDao {
    @Insert
    suspend fun insertSubject(subject: Subject)

    @Query("SELECT id, name, description FROM subject LIMIT 3")
    suspend fun getSummarySubjects(): List<SummarySubject>
}