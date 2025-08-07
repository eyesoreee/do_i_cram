package com.example.doicram

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.doicram.db.dao.AssignmentsDao
import com.example.doicram.db.dao.CoursesDao
import com.example.doicram.db.dao.GradeCategoriesDao
import com.example.doicram.db.dao.GradeScaleDao
import com.example.doicram.db.entities.Assignments
import com.example.doicram.db.entities.Courses
import com.example.doicram.db.entities.GradeCategories
import com.example.doicram.db.entities.GradeScale

@Database(
    entities = [Courses::class, GradeCategories::class, Assignments::class, GradeScale::class],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
    abstract fun gradeCategoriesDao(): GradeCategoriesDao
    abstract fun assignmentsDao(): AssignmentsDao
    abstract fun gradeScaleDao(): GradeScaleDao
}