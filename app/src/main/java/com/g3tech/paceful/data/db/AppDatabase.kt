package com.g3tech.paceful.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.g3tech.paceful.data.db.converters.LocalDateConverter
import com.g3tech.paceful.data.db.daos.StudyDao
import com.g3tech.paceful.data.db.daos.StudyStatusDao
import com.g3tech.paceful.data.db.entities.Study
import com.g3tech.paceful.data.db.entities.StudyStatus
import com.g3tech.paceful.data.db.entities.Subject
import com.g3tech.paceful.data.db.entities.Topic

@Database(
    entities = [Subject::class, Study::class, Topic::class, StudyStatus::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studyDao(): StudyDao
    abstract fun studyStatusDao(): StudyStatusDao
}