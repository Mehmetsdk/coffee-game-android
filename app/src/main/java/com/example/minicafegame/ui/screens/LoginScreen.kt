package com.example.minicafegame.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minicafegame.ui.theme.CoffeeBrown
import com.example.minicafegame.ui.theme.CreamLight
import com.example.minicafegame.ui.theme.CreamWhite
import com.example.minicafegame.ui.theme.GoldenYellow
import com.example.minicafegame.viewmodel.AuthState
import com.example.minicafegame.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            val s = authState as AuthState.Success
            onLoginSuccess(s.token, s.username)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(CoffeeBrown)) {
        // Üst dekoratif alan
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("☕", fontSize = 64.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Coffee Game",
                style = MaterialTheme.typography.headlineLarge,
                color = CreamWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Kahve yap, puan kazan!",
                style = MaterialTheme.typography.bodyMedium,
                color = GoldenYellow
            )
        }

        // Alt kart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(CreamLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Giriş Yap",
                    style = MaterialTheme.typography.headlineSmall,
                    color = CoffeeBrown,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CoffeeBrown,
                        focusedLabelColor = CoffeeBrown,
                        cursorColor = CoffeeBrown
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Şifre") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CoffeeBrown,
                        focusedLabelColor = CoffeeBrown,
                        cursorColor = CoffeeBrown
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (authState is AuthState.Error) {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.login(email, password) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CoffeeBrown),
                    enabled = authState !is AuthState.Loading
                ) {
                    if (authState is AuthState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = CreamWhite,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Giriş Yap", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Hesabın yok mu?", color = CoffeeBrown)
                    TextButton(onClick = onNavigateToRegister) {
                        Text("Kayıt ol", color = GoldenYellow, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
