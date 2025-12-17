
package com.diego.proyecto.screens.practice

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.proyecto.data.model.Routine
import com.diego.proyecto.data.model.RoutineExercise
import com.diego.proyecto.data.repository.PracticeRepository
import com.diego.proyecto.network.TokenManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PracticeViewModel : ViewModel() {

    private val repository = PracticeRepository()

    var routineName by mutableStateOf("")
    var description by mutableStateOf("")
    var estimatedDuration by mutableStateOf("")
    var exercises by mutableStateOf(listOf<RoutineExercise>())

    private val _saveState = MutableSharedFlow<SaveState>()
    val saveState = _saveState.asSharedFlow()

    fun addExercise(name: String, desc: String, time: String, type: String) {
        val newExercise = RoutineExercise(
            name = name,
            description = desc,
            durationMinutes = time.toIntOrNull() ?: 0,
            order = exercises.size + 1,
            type = type
        )
        exercises = exercises + newExercise
    }

    fun removeExercise(index: Int) {
        exercises = exercises.toMutableList().apply { removeAt(index) }
    }

    fun saveRoutine() {
        viewModelScope.launch {
            val routine = Routine(
                userId = TokenManager.userId ?: 1,
                name = routineName,
                description = description,
                estimatedDurationMinutes = estimatedDuration.toIntOrNull() ?: 0,
                daysOfWeek = "Lun,Mar,Mie,Jue,Vie", // O un valor predeterminado/configurable
                exercises = exercises
            )

            try {
                Log.d("PracticeViewModel", "Saving routine: $routine")
                val success = repository.saveRoutine(routine)
                if (success) {
                    Log.d("PracticeViewModel", "Routine saved successfully")
                    _saveState.emit(SaveState.Success)
                } else {
                    Log.e("PracticeViewModel", "Failed to save routine")
                    _saveState.emit(SaveState.Error("No se pudo guardar la rutina"))
                }
            } catch (e: Exception) {
                Log.e("PracticeViewModel", "Error al guardar la rutina: ${e.message}", e)
                _saveState.emit(SaveState.Error(e.message ?: "Error desconocido"))
            }
        }
    }
}

sealed class SaveState {
    object Success : SaveState()
    data class Error(val message: String) : SaveState()
}
