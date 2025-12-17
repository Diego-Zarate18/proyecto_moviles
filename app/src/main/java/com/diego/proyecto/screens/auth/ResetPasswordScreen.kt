package com.diego.proyecto.screens.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diego.proyecto.R
import com.diego.proyecto.navigation.ScreenRoutes
import com.diego.proyecto.ui.theme.ColorButton
import com.diego.proyecto.ui.theme.ColorFin
import com.diego.proyecto.ui.theme.ColorFondoInicio
import com.diego.proyecto.ui.theme.ColorTextoBlanco
import com.diego.proyecto.ui.theme.ColorTextoNegro

@Composable
fun ResetPasswordScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(ColorFondoInicio, ColorFin)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_b),
                contentDescription = "Logo",
                modifier = Modifier.height(60.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Enter your email to reset password",
                color = ColorTextoBlanco,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint = ColorTextoBlanco
                    )
                },
                colors = textFieldColors(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "Reset link sent to $email", Toast.LENGTH_SHORT).show()
                    navController.navigate(ScreenRoutes.LOGIN_SCREEN)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = ColorTextoBlanco)
            ) {
                Text(
                    text = "Send Reset Link",
                    color = ColorTextoNegro,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.LightGray, fontSize = 16.sp)) {
                        append("Back to ")
                    }
                    pushStringAnnotation(tag = "LOGIN", annotation = "login")
                    withStyle(style = SpanStyle(color = ColorButton, fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
                        append("Log In")
                    }
                    pop()
                },
                onClick = { offset ->
                    navController.navigate(ScreenRoutes.LOGIN_SCREEN)
                },
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(textAlign = TextAlign.Center)
            )
        }
    }
}

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    unfocusedContainerColor = Color.Transparent,
    focusedContainerColor = Color.Transparent,
    focusedTextColor = ColorTextoBlanco,
    unfocusedTextColor = ColorTextoBlanco,
    cursorColor = ColorTextoBlanco,
    focusedLabelColor = ColorTextoBlanco,
    unfocusedLabelColor = Color.LightGray,
    focusedIndicatorColor = ColorTextoBlanco,
    unfocusedIndicatorColor = Color.LightGray
)
