package com.example.doicram.dashboard

data class DashboardState(
    val cgpa: Double? = null,
    val activeCourses: ActiveCoursesInfo? = null,
    val pendingAssignments: PendingAssignmentsInfo? = null,
    val needAttention: NeedAttentionInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)