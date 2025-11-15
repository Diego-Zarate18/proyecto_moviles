package com.diego.proyecto.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.diego.proyecto.R
import com.diego.proyecto.ui.components.BottomNavigationBar
import com.diego.proyecto.ui.components.Header
import com.diego.proyecto.ui.components.PracticeCard
import com.diego.proyecto.ui.theme.ColorFin
import com.diego.proyecto.ui.theme.ColorFondoInicio
import com.diego.proyecto.ui.theme.ColorIconsBar
import com.diego.proyecto.ui.theme.ColorTextoBlanco
import com.diego.proyecto.ui.theme.ColorWeek


data class ScheduledSession(
    val id: Int,
    val title: String,
    val date: String,
    val imageUrl: Int
)

data class Practice(
    val id: Int,
    val title: String,
    val type: String,
    val date: String,
    val imageUrl: Int
)

val dummyScheduledSessions = listOf(
    ScheduledSession(1, "Next practice", "15/10/2025", R.drawable.example),
    ScheduledSession(2, "Next practice", "20/10/2025", R.drawable.example)
)

val dummyPastSessions = listOf(
    Practice(1, "Practice #1", "Acordes", "12/10/2025", R.drawable.example),
    Practice(2, "Practice #1", "Ritmo", "10/10/2025", R.drawable.example),
    Practice(3, "Practice #1", "Escalas", "08/10/2025", R.drawable.example)
)

val weekDays = listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sá", "Do")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = ColorFondoInicio
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            item {
                Header()
            }


            item {
                WeeklyProgress(modifier = Modifier.padding(horizontal = 16.dp))
            }


            item {
                ScheduledSessions(
                    sessions = dummyScheduledSessions,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }


            item {
                Text(
                    text = "Sesiones pasadas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTextoBlanco,
                    modifier = Modifier.padding(16.dp)
                )
            }


            items(dummyPastSessions) { session ->
                PracticeCard(
                    practice = session,
                    onClick = {  },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun WeeklyProgress(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Progreso semanal",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = ColorTextoBlanco
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weekDays.forEach { day ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (day == "Ju") ColorWeek else Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = day, color = ColorTextoBlanco, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun ScheduledSessions(sessions: List<ScheduledSession>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Sesiones programadas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = ColorTextoBlanco,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sessions) { session ->
                ScheduledSessionCard(session = session)
            }
        }
    }
}

@Composable
fun ScheduledSessionCard(session: ScheduledSession) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable {  },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Imagen", color = ColorTextoBlanco)
            }
            Text(
                text = session.title,
                fontWeight = FontWeight.Bold,
                color = ColorTextoBlanco,
                modifier = Modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp)
            )
            Text(
                text = session.date,
                fontSize = 12.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}