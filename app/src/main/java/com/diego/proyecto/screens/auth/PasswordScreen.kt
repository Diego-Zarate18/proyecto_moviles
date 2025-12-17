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
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.diego.proyecto.network.TokenManager
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diego.proyecto.R
import com.diego.proyecto.data.repository.AuthRepository
import com.diego.proyecto.navigation.ScreenRoutes
import com.diego.proyecto.ui.theme.ColorButton
import com.diego.proyecto.ui.theme.ColorFin
import com.diego.proyecto.ui.theme.ColorInicio
import com.diego.proyecto.ui.theme.ColorTextoBlanco
import com.diego.proyecto.ui.theme.ColorTextoNegro
import kotlinx.coroutines.launch

@Composable
fun PasswordScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val repository = remember { AuthRepository() }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(ColorInicio, ColorFin)
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
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    scope.launch {
                        val response = repository.login(email, password)
                        if (response != null) {
                            Log.d("Login", "Success: ${response.token}")
                            TokenManager.saveToken(response.token, response.userId)
                            navController.navigate(ScreenRoutes.HOME_SCREEN)
                        } else {
                            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = ColorTextoBlanco)
            ) {
                Text(
                    text = "Log In",
                    color = ColorTextoNegro,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.LightGray, fontSize = 14.sp)) {
                        append("Forgot Password?")
                    }
                },
                onClick = {
                    navController.navigate(ScreenRoutes.PASSWORD_SCREEN) // Assuming existing password screen is now reset pass? Or create new?
                    // User said PasswordScreen IS Login. But Reset Password link existed on Login.
                    // The old LoginScreen had a link to Reset Password.
                    // Since I am repurposing PasswordScreen to be Login, I should point "Forgot Password" to maybe a new ResetPassword screen
                    // or keep it if "ResetPasswordScreen" exists.
                    // I see "ResetPasswordScreen.kt" in list_files earlier. So I will route to it if I find the route.
                    // ScreenRoutes probably has PASSWORD_SCREEN which was the old Login.
                    // I will check ScreenRoutes in a moment.
                },
                modifier = Modifier.align(Alignment.End)
            )
            
            Spacer(modifier = Modifier.height(40.dp))

            ClickableRegisterText(navController)
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
private fun ClickableRegisterText(navController: NavController) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.LightGray, fontSize = 16.sp)) {
            append("Don't have an account? ")
        }

        pushStringAnnotation(tag = "REGISTER", annotation = "register")
        withStyle(style = SpanStyle(color = ColorButton, fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
            append("Sign Up")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "REGISTER", start = offset, end = offset)
                .firstOrNull()?.let {
                    navController.navigate(ScreenRoutes.REGISTER_SCREEN)
                }
        },
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(textAlign = TextAlign.Center)
    )
}
