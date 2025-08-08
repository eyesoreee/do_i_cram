package com.example.doicram.db.repo

import com.example.doicram.dashboard.PendingAssignmentsInfo
import com.example.doicram.db.dao.AssignmentsDao
import com.example.doicram.db.entities.Assignments
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssignmentsRepositoryImpl @Inject constructor(
    private val assignmentsDao: AssignmentsDao
) : AssignmentsRepository {

    override suspend fun addAssignment(assignment: Assignments): Long {
        return assignmentsDao.addAssignment(assignment)
    }

    override suspend fun updateAssignment(assignment: Assignments) {
        assignmentsDao.updateAssignment(assignment)
    }

    override suspend fun getAssignmentsForCategory(categoryId: Int): List<Assignments> {
        return assignmentsDao.getAssignmentsForCategory(categoryId)
    }

    override suspend fun getAssignmentById(assignmentId: Int): Assignments? {
        return assignmentsDao.getAssignmentById(assignmentId)
    }

    override suspend fun deleteAssignment(assignment: Assignments) {
        assignmentsDao.deleteAssignment(assignment)
    }

    override suspend fun deleteAssignmentsForCategory(categoryId: Int) {
        assignmentsDao.deleteAssignmentsForCategory(categoryId)
    }

    override suspend fun getPendingAssignmentsInfo(): PendingAssignmentsInfo {
        return assignmentsDao.getPendingAssignmentsInfo()
    }

    override fun getPendingAssignmentsInfoFlow(): Flow<PendingAssignmentsInfo> {
        return assignmentsDao.getPendingAssignmentsInfoFlow()
    }
}