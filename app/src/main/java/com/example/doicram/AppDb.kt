package com.example.doicram

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.doicram.courses.db.dao.CoursesDao
import com.example.doicram.courses.db.entities.Courses

@Database(entities = [Courses::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
}