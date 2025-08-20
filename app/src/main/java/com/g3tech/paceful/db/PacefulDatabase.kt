package com.g3tech.paceful.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.g3tech.paceful.db.converters.DateToLongConverter
import com.g3tech.paceful.db.daos.StudyDao
import com.g3tech.paceful.db.daos.StudyStatusDao
import com.g3tech.paceful.db.entities.Study
import com.g3tech.paceful.db.entities.StudyStatus
import com.g3tech.paceful.db.entities.Topic

@Database(entities = [Study::class, Topic::class, StudyStatus::class], version = 1)
@TypeConverters(DateToLongConverter::class)
abstract class PacefulDatabase: RoomDatabase() {
    abstract fun studyDao(): StudyDao
    abstract fun studyStatusDao(): StudyStatusDao
}