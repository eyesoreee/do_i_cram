package com.example.doicram.db.repo

import com.example.doicram.db.dao.GradeScaleDao
import com.example.doicram.db.entities.GradeScale
import javax.inject.Inject

class GradeScaleRepositoryImpl @Inject constructor(
    private val gradeScaleDao: GradeScaleDao
) : GradeScaleRepository {

    override suspend fun insertGradeScale(gradeScale: GradeScale): Long {
        return gradeScaleDao.insertGradeScale(gradeScale)
    }

    override suspend fun insertGradeScales(gradeScales: List<GradeScale>): List<Long> {
        return gradeScaleDao.insertGradeScales(gradeScales)
    }

    override suspend fun deleteGradeScale(gradeScale: GradeScale): Int {
        return gradeScaleDao.deleteGradeScale(gradeScale)
    }

    override suspend fun getAllGradeScales(): List<GradeScale> {
        return gradeScaleDao.getAllGradeScales()
    }

    override suspend fun getGradeScalesForCourse(courseId: Int): List<GradeScale> {
        val customScales = gradeScaleDao.getByCourseId(courseId)
        return customScales.ifEmpty {
            gradeScaleDao.getDefaultScales()
        }
    }

    override suspend fun getDefaultGradeScales(): List<GradeScale> {
        return gradeScaleDao.getDefaultScales()
    }

    override suspend fun getGradeScaleById(id: Int): GradeScale? {
        return gradeScaleDao.getGradeScaleById(id)
    }

    override suspend fun getGradeScaleForPercentage(
        courseId: Int,
        percentage: Double
    ): GradeScale? {
        val scales = getGradeScalesForCourse(courseId)
        return GradeScale.getGpaForPercentage(percentage, scales)
    }

    override suspend fun updateGradeScale(gradeScale: GradeScale): Int {
        return gradeScaleDao.updateGradeScale(gradeScale)
    }
}

