package com.example.doicram

import android.content.Context
import com.example.doicram.courses.db.dao.CoursesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return MyApp.database
    }

    @Provides
    @Singleton
    fun provideCoursesDao(database: AppDatabase): CoursesDao {
        return database.coursesDao()
    }
}