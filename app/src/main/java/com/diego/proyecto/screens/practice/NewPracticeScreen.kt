
package com.diego.proyecto.screens.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diego.proyecto.data.model.RoutineExercise
import com.diego.proyecto.ui.theme.DarkBackground
import com.diego.proyecto.ui.theme.DarkGreyBox
import com.diego.proyecto.ui.theme.GreenButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewPracticeScreen(navController: NavController, viewModel: PracticeViewModel = viewModel()) {
    var showAddExerciseDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.saveState.collectLatest {
            when (it) {
                is SaveState.Success -> {
                    navController.popBackStack()
                }
                is SaveState.Error -> {
                    snackbarHostState.showSnackbar(
                        message = "Error: ${it.message}",
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    Scaffold(
        containerColor = DarkBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddExerciseDialog = true },
                containerColor = GreenButton,
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Exercise")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .background(DarkBackground)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "New Routine",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Inputs de la Rutina
            PracticeInput(value = viewModel.routineName, onValueChange = { viewModel.routineName = it }, label = "Routine Name")
            Spacer(modifier = Modifier.height(16.dp))

            PracticeInput(value = viewModel.description, onValueChange = { viewModel.description = it }, label = "Description", maxLines = 3)
            Spacer(modifier = Modifier.height(16.dp))

            PracticeInput(value = viewModel.estimatedDuration, onValueChange = { viewModel.estimatedDuration = it }, label = "Est. Duration (min)")

            Spacer(modifier = Modifier.height(24.dp))

            Text("Exercises", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            // Lista de ejercicios
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(viewModel.exercises) { index, exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        onDelete = { viewModel.removeExercise(index) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Button(
                onClick = { viewModel.saveRoutine() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Text("Save Routine", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showAddExerciseDialog) {
        AddExerciseDialog(
            onDismiss = { showAddExerciseDialog = false },
            onAdd = { name, desc, time, type ->
                viewModel.addExercise(name, desc, time, type)
                showAddExerciseDialog = false
            }
        )
    }
}

@Composable
fun PracticeInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLines: Int = 1
) {
    Column {
        Text(label, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = maxLines == 1,
            maxLines = maxLines,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = DarkGreyBox,
                unfocusedContainerColor = DarkGreyBox,
                focusedBorderColor = GreenButton,
                unfocusedBorderColor = DarkGreyBox,
                cursorColor = GreenButton
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun ExerciseItem(exercise: RoutineExercise, onDelete: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkGreyBox),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(exercise.name, color = Color.White, fontWeight = FontWeight.Bold)
                Text("${exercise.durationMinutes} min - ${exercise.type}", color = Color.Gray, fontSize = 12.sp)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
fun AddExerciseDialog(onDismiss: () -> Unit, onAdd: (String, String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Técnica") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkBackground,
        title = { Text("Add Exercise", color = Color.White) },
        text = {
            Column {
                PracticeInput(name, { name = it }, "Name")
                Spacer(modifier = Modifier.height(8.dp))
                PracticeInput(desc, { desc = it }, "Description")
                Spacer(modifier = Modifier.height(8.dp))
                PracticeInput(time, { time = it }, "Duration (min)")
                Spacer(modifier = Modifier.height(8.dp))
                // Simple Dropdown or Text for Type (Simplificado como Text por ahora)
                PracticeInput(type, { type = it }, "Type (e.g. Técnica, Repertorio)")
            }
        },
        confirmButton = {
            Button(
                onClick = { onAdd(name, desc, time, type) },
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Text("Add", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.White)
            }
        }
    )
}
