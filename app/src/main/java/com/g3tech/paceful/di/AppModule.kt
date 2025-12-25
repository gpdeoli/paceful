package com.g3tech.paceful.di

import androidx.room.Room
import com.g3tech.paceful.data.StudyRepositoryImpl
import com.g3tech.paceful.data.db.AppDatabase
import com.g3tech.paceful.data.db.callbacks.PrepopulateStudyStatusCallback
import com.g3tech.paceful.domain.repositories.StudyRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> {
        val callback =
            PrepopulateStudyStatusCallback(studyStatusDaoProvider = { get<AppDatabase>().studyStatusDao() })
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "paceful_database"
        ).addCallback(callback).build()
    }

    single<StudyRepository> {
        StudyRepositoryImpl(get<AppDatabase>().studyDao())
    }
}