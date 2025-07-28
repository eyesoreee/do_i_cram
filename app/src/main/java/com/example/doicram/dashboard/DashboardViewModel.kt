import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class CardType {
    GPA, ACTIVE_COURSES, UPCOMING_DUE, NEED_ATTENTION
}

data class DashboardCard(
    val title: String,
    val mainValue: String,
    val secondaryValue: String,
    val bottomText: String,
    val cardType: CardType
)

data class DashboardState(
    val gpaCard: DashboardCard? = null,
    val activeCoursesCard: DashboardCard? = null,
    val upcomingDueCard: DashboardCard? = null,
    val needAttentionCard: DashboardCard? = null
)

class DashboardViewModel : ViewModel() {
    private val _state = MutableStateFlow<DashboardState>(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
}