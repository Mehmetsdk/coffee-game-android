package com.example.minicafegame.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.minicafegame.network.ApiClient
import com.example.minicafegame.network.PointEntry
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(token: String, username: String) {
    var totalPoints by remember { mutableStateOf(0) }
    var history by remember { mutableStateOf<List<PointEntry>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            val response = ApiClient.api.getPoints("Bearer $token")
            totalPoints = response.totalPoints
            history = response.history
        } catch (e: Exception) {
            // hata durumunda boş liste kalır
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Merhaba, $username!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Toplam Puan", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "$totalPoints ⭐",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Test butonu: oyun kazanıldığında puan eklemek için
        Button(
            onClick = {
                scope.launch {
                    try {
                        ApiClient.api.addPoints("Bearer $token", com.example.minicafegame.network.AddPointRequest(3, "game_win"))
                        val response = ApiClient.api.getPoints("Bearer $token")
                        totalPoints = response.totalPoints
                        history = response.history
                    } catch (e: Exception) { }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Oyun Kazandım! (+3 puan)")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Puan Geçmişi", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(history) { entry ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(entry.reason)
                            Text("+${entry.amount} ⭐")
                        }
                    }
                }
            }
        }
    }
}
