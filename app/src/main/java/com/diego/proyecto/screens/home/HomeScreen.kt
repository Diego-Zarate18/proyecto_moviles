package com.diego.proyecto.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.diego.proyecto.R
import com.diego.proyecto.data.model.PracticeSession
import com.diego.proyecto.data.model.Routine
import com.diego.proyecto.navigation.ScreenRoutes
import com.diego.proyecto.ui.theme.*

data class DayProgress (
    val dayName: String,
    val dayNumber: String,
    val isCompleted: Boolean
)

// Lista de URLs de imágenes de música/rock de Unsplash
object MusicImages {
    val images = listOf(
        "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=400",  // Guitarra eléctrica
        "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=400",  // Concierto rock
        "https://images.unsplash.com/photo-1514320291840-2e0a9bf2a9ae?w=400",  // Música
        "https://images.unsplash.com/photo-1510915361894-db8b60106cb1?w=400",  // DJ/Música
        "https://images.unsplash.com/photo-1598488035139-bdbb2231ce04?w=400",  // Estudio música
        "https://images.unsplash.com/photo-1507838153414-b4b713384a76?w=400",  // Piano
        "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=400",  // Concierto
        "https://images.unsplash.com/photo-1485579149621-3123dd979885?w=400",  // Batería
        "https://images.unsplash.com/photo-1524650359799-842906ca1c06?w=400",  // Rock band
        "https://images.unsplash.com/photo-1508700115892-45ecd05ae2ad?w=400"   // Guitarrista
    )

    fun getImageForRoutine(routineId: Int?): String {
        val index = (routineId ?: 0) % images.size
        return images[index]
    }
}

// --- PANTALLA PRINCIPAL ---
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) },
        containerColor = ColorFondoApp
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. Header / Logo con botón refresh
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Music",
                        color = ColorTextoBlanco,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "🎸",
                        fontSize = 20.sp
                    )
                }
                IconButton(onClick = { viewModel.refresh() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = ColorTextoBlanco
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = ColorBotonMas,
                    modifier = Modifier.padding(32.dp)
                )
            } else {
                // 2. Progreso Semanal
                SectionTitle(text = "Progreso semanal")

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.weekProgress) { day ->
                        DayItem(day)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 3. Sesiones Programadas (Rutinas activas)
                SectionTitle(text = "Sesiones programadas")

                if (uiState.routines.isEmpty()) {
                    Text(
                        text = "No hay rutinas programadas",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(uiState.routines) { index, routine ->
                            ScheduledSessionCard(routine, index)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 4. Sesiones Pasadas
                SectionTitle(text = "Sesiones pasadas")

                if (uiState.sessions.isEmpty()) {
                    Text(
                        text = "No hay sesiones registradas",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    uiState.sessions.take(5).forEachIndexed { index, session ->
                        PastSessionCard(session, index)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            // Espacio extra al final
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

// --- COMPONENTES AUXILIARES ---
@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        color = ColorTextoBlanco,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

@Composable
fun DayItem(day: DayProgress) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (day.isCompleted) ColorResaltadoDia else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.dayNumber,
                color = ColorTextoBlanco,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = day.dayName,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ScheduledSessionCard(routine: Routine, index: Int) {
    val imageUrl = MusicImages.getImageForRoutine(routine.id ?: index)

    Column(
        modifier = Modifier.width(140.dp)
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.DarkGray)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = routine.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Overlay oscuro para leer el texto
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            // Fecha/días en la esquina
            Text(
                text = "📅 ${routine.daysOfWeek}",
                color = ColorTextoBlanco,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
        Text(
            text = routine.name,
            color = ColorTextoBlanco,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 6.dp)
        )
        Text(
            text = "${routine.estimatedDurationMinutes} min",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun PastSessionCard(session: PracticeSession, index: Int) {
    val imageUrl = MusicImages.getImageForRoutine(session.routineId ?: session.odlRutinaId ?: index)
    val isCompleted = session.completed == true || session.completada == true
    val duration = session.durationMinutes ?: session.duracionMinutos ?: 0
    val date = session.date ?: session.fecha ?: ""
    val sessionName = "Práctica #${String.format("%03d", session.id ?: (index + 1))}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF333333), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen de la sesión
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = sessionName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Indicador de completado
            if (isCompleted) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "✓", color = Color.Green, fontSize = 24.sp)
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = sessionName,
                color = ColorTextoBlanco,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "$duration min",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }

        Text(
            text = formatDate(date),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

// Función para formatear fecha
fun formatDate(dateStr: String): String {
    return try {
        if (dateStr.length >= 10) {
            val parts = dateStr.substring(0, 10).split("-")
            if (parts.size >= 3) {
                "${parts[2]}/${parts[1]}/${parts[0]}"
            } else dateStr
        } else dateStr
    } catch (e: Exception) {
        dateStr
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(
        containerColor = ColorFondoApp,
        contentColor = ColorTextoBlanco
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate(ScreenRoutes.HOME_SCREEN) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = ColorTextoBlanco
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ScreenRoutes.NEW_PRACTICE_SCREEN) },
            icon = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(ColorBotonMas),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = ColorTextoBlanco
                    )
                }
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Navegación aquí */ },
            icon = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "More",
                        tint = ColorTextoBlanco
                    )
                    Text(
                        text = "más abajo",
                        fontSize = 10.sp,
                        color = ColorTextoBlanco
                    )
                }
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
