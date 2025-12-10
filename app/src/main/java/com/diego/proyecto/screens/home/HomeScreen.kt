package com.diego.proyecto.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diego.proyecto.R
import com.diego.proyecto.ui.theme.*

// Data Class para el progreso semanal
data class DayProgress(
    val dayName: String,
    val dayNumber: String,
    val isCompleted: Boolean
)

// Data Class para sesiones programadas
data class ScheduledSession(
    val id: Int,
    val date: String,
    val imageRes: Int,
    val title: String
)

// Data Class para sesiones pasadas
data class PastSession(
    val id: Int,
    val practiceName: String,
    val duration: String,
    val date: String,
    val imageRes: Int
)

@Composable
fun HomeScreen(navController: NavController) {
    // Datos de ejemplo
    val weekDays = listOf(
        DayProgress("Lun", "12", true),
        DayProgress("Mar", "13", true),
        DayProgress("Mié", "14", false),
        DayProgress("Jue", "15", false),
        DayProgress("Vie", "16", false),
        DayProgress("Sáb", "17", false),
        DayProgress("Dom", "18", false)
    )

    val scheduledSessions = listOf(
        ScheduledSession(1, "15 Oct", R.drawable.ic_launcher_background, "Sesión 1"), // Usando placeholder
        ScheduledSession(2, "17 Oct", R.drawable.ic_launcher_background, "Sesión 2"),
        ScheduledSession(3, "19 Oct", R.drawable.ic_launcher_background, "Sesión 3")
    )

    val pastSessions = listOf(
        PastSession(1, "Practica #001", "30 min", "12 Oct", R.drawable.ic_launcher_background),
        PastSession(2, "Practica #002", "45 min", "10 Oct", R.drawable.ic_launcher_background),
        PastSession(3, "Practica #003", "20 min", "08 Oct", R.drawable.ic_launcher_background)
    )

    Scaffold(
        bottomBar = { BottomNavBar() },
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
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_b),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(50.dp)
                    .padding(bottom = 16.dp)
            )

            // Progreso Semanal
            Text(
                text = "Progreso semanal",
                color = ColorTextoBlanco,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(weekDays) { day ->
                    DayItem(day)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sesiones Programadas
            Text(
                text = "Sesiones programadas",
                color = ColorTextoBlanco,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(scheduledSessions) { session ->
                    ScheduledSessionItem(session)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sesiones Pasadas
            Text(
                text = "Sesiones pasadas",
                color = ColorTextoBlanco,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            pastSessions.forEach { session ->
                PastSessionItem(session)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
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
                .background(if (day.isCompleted) ColorResaltadoDia else Color.Transparent)
                .then(
                    if (!day.isCompleted) Modifier // Borde si no está completado, opcional
                    else Modifier
                ),
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
fun ScheduledSessionItem(session: ScheduledSession) {
    Column(
        modifier = Modifier.width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.DarkGray)
        ) {
            Image(
                painter = painterResource(id = session.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Overlay oscuro opcional para legibilidad
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
            Text(
                text = session.date,
                color = ColorTextoBlanco,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun PastSessionItem(session: PastSession) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF333333), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = session.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = session.practiceName,
                color = ColorTextoBlanco,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = session.date,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        Text(
            text = session.duration,
            color = ColorTextoBlanco,
            fontSize = 14.sp
        )
    }
}

@Composable
fun BottomNavBar() {
    NavigationBar(
        containerColor = ColorFondoApp,
        contentColor = ColorTextoBlanco
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
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
            onClick = { /*TODO*/ },
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
            onClick = { /*TODO*/ },
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
