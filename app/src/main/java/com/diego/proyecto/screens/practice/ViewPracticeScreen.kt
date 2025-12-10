package com.diego.proyecto.screens.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PracticeDetails(
    val id: String,
    val title: String,
    val date: String,
    val duration: String,
    val description: String,
    val mediaResources: List<Int>
)

@Composable
fun ViewPracticeScreen(practice: PracticeDetails) {
    Scaffold(
        containerColor = DarkBackground
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
                text = "Musio 🎶",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(24.dp))
            InfoBlock(title = practice.title)
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(DarkGreyBox)
                    .padding(16.dp)
            ) {
                Text("Date: ${practice.date}", color = Color.White, fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Duration: ${practice.duration}", color = Color.White.copy(alpha = 0.7f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoBlock(title = "Description", content = practice.description)

            Spacer(modifier = Modifier.height(16.dp))
            MultimediaBlock(mediaResources = practice.mediaResources)
        }
    }
}

@Composable
fun InfoBlock(title: String, content: String? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DarkGreyBox)
            .padding(16.dp)
    ) {
        Text(
            title,
            color = if (content == null) Color.White else Color.White.copy(alpha = 0.7f),
            fontWeight = if (content == null) FontWeight.SemiBold else FontWeight.Normal
        )
        if (content != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(content, color = Color.White)
        }
    }
}

@Composable
fun MultimediaBlock(mediaResources: List<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DarkGreyBox)
            .padding(16.dp)
    ) {

        Text("Multimedia", color = Color.White.copy(alpha = 0.7f))

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            mediaResources.forEach { resourceId ->
                ImagePlaceholder(resourceId)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {  }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Practice", tint = Color.Gray)
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {  }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Practice", tint = Color.Gray)
            }
        }
    }
}

@Composable
fun ImagePlaceholder(resourceId: Int) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
    ) {
        Text(
            text = "Imagen $resourceId",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ViewPracticePreview() {
    val samplePractice = PracticeDetails(
        id = "123",
        title = "Practice #8",
        date = "Oct, 31, 2025",
        duration = "1 hour",
        description = "Estuve aprendiendo sobre teoría musical y muchas cosas más, centrándome en las escalas mayores y menores.",
        mediaResources = listOf(1, 2)
    )
    ViewPracticeScreen(practice = samplePractice)
}