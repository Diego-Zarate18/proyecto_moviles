package com.diego.proyecto.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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


data class ScheduledSession(
    val id: Int,
    val date: String,
)

    val id: Int,
    val date: String,
)

@Composable
fun HomeScreen(navController: NavController) {

    Scaffold(
    ) { paddingValues ->
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
                )

                Text(
                    fontWeight = FontWeight.Bold,
                    color = ColorTextoBlanco,
                )

    }
}


        Text(
            fontWeight = FontWeight.Bold,
        )
        ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                    contentAlignment = Alignment.Center
                ) {
        Text(
            color = ColorTextoBlanco,
        )
        }
    }
}

@Composable
        modifier = Modifier
    ) {
            Box(
                modifier = Modifier
            Text(
                fontWeight = FontWeight.Bold,
                color = ColorTextoBlanco,
            )
            Text(
                text = session.date,
            )
        }
    }
}

@Composable
}