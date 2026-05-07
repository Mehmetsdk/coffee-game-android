package com.example.minicafegame.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minicafegame.network.AddPointRequest
import com.example.minicafegame.network.ApiClient
import com.example.minicafegame.network.PointEntry
import com.example.minicafegame.ui.theme.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
            // bağlantı hatası
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamLight)
    ) {
        // Üst başlık alanı
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(CoffeeBrown, CoffeeMedium))
                )
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Column {
                Text(
                    "Merhaba, $username! ☕",
                    style = MaterialTheme.typography.titleLarge,
                    color = CreamWhite,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Bugün ne içmek istersin?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GoldenYellow
                )
            }
        }

        // Puan kartı
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(listOf(WarmAmber, GoldenYellow))
                )
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Toplam Puan",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CoffeeDark,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "$totalPoints",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeBrown,
                        modifier = Modifier.animateContentSize()
                    )
                }
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(CoffeeBrown.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⭐", fontSize = 36.sp)
                }
            }
        }

        // Oyun butonu
        Button(
            onClick = {
                scope.launch {
                    try {
                        ApiClient.api.addPoints(
                            "Bearer $token",
                            AddPointRequest(3, "game_win")
                        )
                        val response = ApiClient.api.getPoints("Bearer $token")
                        totalPoints = response.totalPoints
                        history = response.history
                    } catch (e: Exception) { }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CoffeeBrown)
        ) {
            Text("☕  Oyun Oyna (+3 Puan)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Geçmiş başlık
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Puan Geçmişi",
                style = MaterialTheme.typography.titleMedium,
                color = CoffeeBrown,
                fontWeight = FontWeight.Bold
            )
            Text(
                "${history.size} kayıt",
                style = MaterialTheme.typography.bodySmall,
                color = CoffeeMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = CoffeeBrown)
            }
        } else if (history.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Henüz puan yok. Oynamaya başla!", color = CoffeeMedium)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(history) { entry ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(GoldenYellow.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("☕", fontSize = 18.sp)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        entry.reason.replace("_", " ").replaceFirstChar { it.uppercase() },
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = CoffeeBrown,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
                                            .format(Date(entry.createdAt)),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CoffeeLight
                                    )
                                }
                            }
                            Text(
                                "+${entry.amount} ⭐",
                                fontWeight = FontWeight.Bold,
                                color = WarmAmber,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}
