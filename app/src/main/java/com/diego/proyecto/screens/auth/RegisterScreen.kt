package com.diego.proyecto.screens.auth

import android.util.Log
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.diego.proyecto.R
import com.diego.proyecto.ui.theme.ColorButton
import com.diego.proyecto.ui.theme.ColorFondoFin
import com.diego.proyecto.ui.theme.ColorFondoInicio
import com.diego.proyecto.ui.theme.ColorTextoBlanco
import com.diego.proyecto.ui.theme.ColorTextoNegro

@Composable
fun RegisterScreen(navController: NavController) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(ColorFondoInicio, ColorFondoFin)
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
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_b),
                contentDescription = "Logo",
                modifier = Modifier.height(60.dp)
            )

            Spacer(modifier = Modifier.height(70.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Name Icon",
                        tint = ColorTextoBlanco
                    )
                },
                colors = textFieldColors(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(45.dp))

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

            Spacer(modifier = Modifier.height(45.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon",
                        tint = ColorTextoBlanco
                    )
                },
                colors = textFieldColors(),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = "Toggle password visibility",
                            tint = ColorTextoBlanco
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    Log.d("RegisterScreen", "Name: $name, Email: $email, Pass: $password")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = ColorTextoBlanco)
            ) {
                Text(
                    text = "Sing Up",
                    color = ColorTextoNegro,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))

            ClickableLoginText(navController = navController)
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

@Composable
private fun ClickableLoginText(navController: NavController) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.LightGray, fontSize = 16.sp)) {
            append("Already have an account? ")
        }

        pushStringAnnotation(tag = "LOGIN", annotation = "login")
        withStyle(style = SpanStyle(color = ColorButton, fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
            append("Log In")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                .firstOrNull()?.let {
                    Log.d("RegisterScreen", "Clicked on Log In")

                    navController.popBackStack()
                }
        },
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(textAlign = TextAlign.Center)
    )
}