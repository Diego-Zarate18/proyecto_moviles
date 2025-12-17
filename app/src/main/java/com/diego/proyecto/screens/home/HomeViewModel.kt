package com.diego.proyecto.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.proyecto.data.model.PracticeSession
import com.diego.proyecto.data.model.Routine
import com.diego.proyecto.data.model.SessionStats
import com.diego.proyecto.data.repository.PracticeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class HomeUiState(
    val isLoading: Boolean = true,
    val routines: List<Routine> = emptyList(),
    val sessions: List<PracticeSession> = emptyList(),
    val stats: SessionStats? = null,
    val weekProgress: List<DayProgress> = emptyList(),
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val repository = PracticeRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                // Cargar datos en paralelo
                val routines = repository.getRoutines()
                val sessions = repository.getSessions()
                val stats = repository.getSessionStats()

                Log.d("HomeViewModel", "Loaded ${routines.size} routines, ${sessions.size} sessions")

                // Calcular progreso semanal basado en sesiones completadas
                val weekProgress = calculateWeekProgress(sessions)

                _uiState.value = HomeUiState(
                    isLoading = false,
                    routines = routines,
                    sessions = sessions,
                    stats = stats,
                    weekProgress = weekProgress
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading data", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun calculateWeekProgress(sessions: List<PracticeSession>): List<DayProgress> {
        val today = LocalDate.now()
        val startOfWeek = today.with(DayOfWeek.MONDAY)

        // Obtener fechas de sesiones completadas
        val completedDates = sessions
            .filter { it.completed == true || it.completada == true }
            .mapNotNull { session ->
                try {
                    val dateStr = session.date ?: session.fecha ?: return@mapNotNull null
                    LocalDate.parse(dateStr.substring(0, 10)) // Tomar solo YYYY-MM-DD
                } catch (e: Exception) {
                    null
                }
            }
            .toSet()

        return (0..6).map { dayOffset ->
            val date = startOfWeek.plusDays(dayOffset.toLong())
            val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es"))
                .replaceFirstChar { it.uppercase() }

            DayProgress(
                dayName = dayName,
                dayNumber = date.dayOfMonth.toString(),
                isCompleted = completedDates.contains(date)
            )
        }
    }

    fun refresh() {
        loadData()
    }
}

