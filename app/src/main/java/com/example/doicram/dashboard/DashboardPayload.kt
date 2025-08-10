package com.example.doicram.dashboard

data class DashboardPayload(
    val activeCourses: ActiveCoursesInfo,
    val pendingAssignments: PendingAssignmentsInfo,
    val needAttention: NeedAttentionInfo,
    val cgpa: Double?
)