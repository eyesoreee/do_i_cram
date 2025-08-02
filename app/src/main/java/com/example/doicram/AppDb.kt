package com.example.doicram

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.doicram.courses.db.dao.AssignmentsDao
import com.example.doicram.courses.db.dao.CoursesDao
import com.example.doicram.courses.db.dao.GradeCategoriesDao
import com.example.doicram.courses.db.entities.Assignments
import com.example.doicram.courses.db.entities.Courses
import com.example.doicram.courses.db.entities.GradeCategories

@Database(entities = [Courses::class, GradeCategories::class, Assignments::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
    abstract fun gradeCategoriesDao(): GradeCategoriesDao

    abstract fun assignmentsDao(): AssignmentsDao
}