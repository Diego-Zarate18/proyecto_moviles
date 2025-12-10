package com.diego.proyecto.screens.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diego.proyecto.ui.theme.DarkBackground
import com.diego.proyecto.ui.theme.DarkGreyBox
import com.diego.proyecto.ui.theme.GreenButton

@Composable
fun NewPracticeScreen() {
    var practiceName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

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
            Spacer(modifier = Modifier.height(24.dp))

            Text("Title", color = Color.White.copy(alpha = 0.7f))
            OutlinedTextField(
                value = practiceName,
                onValueChange = { practiceName = it },
                placeholder = { Text("Practice Title", color = Color.Gray) },
                singleLine = true,
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

            Spacer(modifier = Modifier.height(24.dp))

            Text("Description", color = Color.White.copy(alpha = 0.7f))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Description", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(8.dp), maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = DarkGreyBox,
                    unfocusedContainerColor = DarkGreyBox,
                    focusedBorderColor = GreenButton,
                    unfocusedBorderColor = DarkGreyBox,
                    cursorColor = GreenButton
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Text("Create Practice", color = Color.Black, fontWeight = FontWeight.Bold)
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
