package com.example.doicram.db.repo

import com.example.doicram.db.entities.GradeScale

interface GradeScaleRepository {
    suspend fun insertGradeScale(gradeScale: GradeScale): Long
    suspend fun insertGradeScales(gradeScales: List<GradeScale>): List<Long>
    suspend fun updateGradeScale(gradeScale: GradeScale): Int
    suspend fun deleteGradeScale(gradeScale: GradeScale): Int
    suspend fun deleteGradeScales(gradeScales: List<GradeScale>)
    suspend fun getAllGradeScales(): List<GradeScale>
    suspend fun getGradeScalesForCourse(courseId: Int): List<GradeScale>
    suspend fun getDefaultGradeScales(): List<GradeScale>
    suspend fun getGradeScaleById(id: Int): GradeScale?
    suspend fun getGradeScaleForPercentage(courseId: Int, percentage: Double): GradeScale?
}