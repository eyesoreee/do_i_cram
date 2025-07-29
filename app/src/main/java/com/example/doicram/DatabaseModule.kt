package com.example.doicram

import android.content.Context
import androidx.room.Room
import com.example.doicram.courses.db.dao.CoursesDao
import com.example.doicram.courses.db.dao.GradeCategoriesDao
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "do_i_cram.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoursesDao(database: AppDatabase): CoursesDao {
        return database.coursesDao()
    }

    @Provides
    @Singleton
    fun provideGradeCategoriesDao(database: AppDatabase): GradeCategoriesDao {
        return database.gradeCategoriesDao()
    }
}