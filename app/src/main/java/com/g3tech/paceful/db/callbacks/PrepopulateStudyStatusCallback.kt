package com.g3tech.paceful.db.callbacks

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.g3tech.paceful.db.daos.StudyStatusDao
import com.g3tech.paceful.db.entities.StudyStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class PrepopulateStudyStatusCallback @Inject constructor(
    private val studyStatusDaoProvider: Provider<StudyStatusDao>
) : RoomDatabase.Callback() {

    private val studyStatusList = listOf(
        StudyStatus(id = 1, name = "Pending"),
        StudyStatus(id = 2, name = "In Progress"),
        StudyStatus(id = 3, name = "Done"),
        StudyStatus(id = 4, name = "Scheduled")
    )

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prepopulateStudyStatuses()
        }
    }

    private fun prepopulateStudyStatuses() {
        val studyStatusDao = studyStatusDaoProvider.get()
        studyStatusDao.insertAll(studyStatusList)
    }
}