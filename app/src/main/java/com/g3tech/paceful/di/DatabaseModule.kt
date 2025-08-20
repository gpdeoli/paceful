package com.g3tech.paceful.di

import android.content.Context
import androidx.room.Room
import com.g3tech.paceful.db.PacefulDatabase
import com.g3tech.paceful.db.callbacks.PrepopulateStudyStatusCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        callback: PrepopulateStudyStatusCallback
    ): PacefulDatabase {
        return Room.databaseBuilder(context, PacefulDatabase::class.java, "paceful_database")
            .addCallback(callback).build()
    }

    @Provides
    fun provideStudyDao(database: PacefulDatabase) = database.studyDao()

    @Provides
    fun provideStudyStatusDao(database: PacefulDatabase) = database.studyStatusDao()
}