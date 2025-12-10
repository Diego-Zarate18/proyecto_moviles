package com.diego.proyecto.screens.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke

val DarkBackground = Color(0xFF1F1F1F)
val GreenButton = Color(0xFF00C897)
val DarkGreyBox = Color(0xFF333333)

@Composable
fun NewPracticeScreen() {
    var practiceName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var timeHours by remember { mutableStateOf("02") }
    var timeMinutes by remember { mutableStateOf("00") }
    var selectedDate by remember { mutableStateOf("29/10/2025") }
    var selectedHour by remember { mutableStateOf("10:00 AM") }

    Scaffold(
        containerColor = DarkBackground,

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .background(DarkBackground)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Musio 🎶",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "New Practice",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Title", color = Color.White.copy(alpha = 0.7f))
            OutlinedTextField(
                value = practiceName,
                onValueChange = { practiceName = it },
                placeholder = { Text("New Practice", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = DarkGreyBox,
                    unfocusedContainerColor = DarkGreyBox,
                    focusedBorderColor = GreenButton,
                    unfocusedBorderColor = DarkGreyBox,
                    cursorColor = GreenButton
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(DarkGreyBox, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(timeHours, fontSize = 40.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Text(" : ", fontSize = 40.sp, color = Color.White)
                    Text(timeMinutes, fontSize = 40.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Date:", color = Color.White.copy(alpha = 0.7f))
                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = {  },
                        readOnly = true,
                        trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Date") },
                        modifier = Modifier.fillMaxWidth().clickable { }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Hour:", color = Color.White.copy(alpha = 0.7f))
                    OutlinedTextField(
                        value = selectedHour,
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().clickable {  }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Description", color = Color.White.copy(alpha = 0.7f))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Description", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(8.dp),
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = DarkGreyBox,
                    unfocusedContainerColor = DarkGreyBox,
                    focusedBorderColor = GreenButton,
                    unfocusedBorderColor = DarkGreyBox,
                    cursorColor = GreenButton
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(width = 1.dp, color = Color.Gray)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Add, contentDescription = "Add Multimedia", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Multimedia", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Text("Add Practice", color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewPracticePreview() {
    NewPracticeScreen()
}
