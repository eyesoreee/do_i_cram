package com.example.doicram.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doicram.db.repo.AssignmentsRepository
import com.example.doicram.db.repo.CoursesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DashboardPayload(
    val activeCourses: ActiveCoursesInfo,
    val pendingAssignments: PendingAssignmentsInfo,
    val needAttention: NeedAttentionInfo,
    val cgpa: Double?
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val assignmentsRepository: AssignmentsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init {
        loadDashboardWithFlow()
    }

    private fun loadDashboardWithFlow() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                combine(
                    coursesRepository.getActiveCoursesInfoFlow(),
                    assignmentsRepository.getPendingAssignmentsInfoFlow(),
                    coursesRepository.getNeedAttentionInfoFlow(),
                    coursesRepository.getCGPA()
                ) { activeCourses, pendingAssignments, needAttention, cgpa ->
                    DashboardPayload(
                        activeCourses = activeCourses,
                        pendingAssignments = pendingAssignments,
                        needAttention = needAttention,
                        cgpa = cgpa
                    )
                }.collect { dashboard ->
                    _state.update { currentState ->
                        currentState.copy(
                            cgpa = dashboard.cgpa,
                            activeCourses = dashboard.activeCourses,
                            pendingAssignments = dashboard.pendingAssignments,
                            needAttention = dashboard.needAttention.copy(
                                subtext = if (dashboard.needAttention.count > 0) "Below target grade" else null
                            ),
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load dashboard: ${e.localizedMessage ?: "Unknown error"}"
                    )
                }
                e.printStackTrace()
            }
        }
    }
}