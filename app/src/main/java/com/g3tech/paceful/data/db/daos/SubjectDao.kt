package com.g3tech.paceful.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import com.g3tech.paceful.data.db.entities.Subject

@Dao
interface SubjectDao {
    @Insert
    suspend fun insertSubject(vararg subject: Subject)
}