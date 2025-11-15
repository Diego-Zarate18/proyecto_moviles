package com.diego.proyecto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diego.proyecto.screens.home.Practice
import com.diego.proyecto.screens.home.dummyPastSessions
import com.diego.proyecto.ui.theme.ColorTextoBlanco

@Composable
fun PracticeCard(
    practice: Practice,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {

                Text(text = "Img", fontSize = 12.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp))


            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = practice.title,
                    fontWeight = FontWeight.Bold,
                    color = ColorTextoBlanco
                )
                Text(
                    text = practice.type,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = practice.date,
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
fun PracticeCardPreview() {
    PracticeCard(
        practice = dummyPastSessions.first(),
        onClick = {}
    )
}