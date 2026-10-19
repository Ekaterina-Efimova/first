package com.example.first.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.first.ui.model.AppDetails
import com.example.first.ui.model.Category
import kotlin.math.roundToInt

@Composable
fun AppDetailScreen(appName: String, navController: NavController) {
    // Хардкодим данные (в реальности тут был бы запрос к API)
    val app = remember {
        AppDetails(
            name = appName,
            developer = "VK Play",
            category = Category.GAME,
            ageRating = 12,
            size = 223.7f,
            description = "Легендарный рейд героев в Фэнтези РПГ. Станьте героем гильдии и сразите мастера подземелья! Это описание взято из практического репозитория."
        )
    }

    var descriptionCollapsed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { /* Поделиться */ }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Поделиться",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Android,
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = if (app.category == Category.GAME) "Игры" else "Приложения",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = app.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(4.dp))
                Text(text = app.developer, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Row {
                    Column {
                        Text(text = "${app.ageRating}+")
                        Text(text = "Возраст", fontSize = 10.sp)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(text = "${app.size.roundToInt()} MB")
                        Text(text = "Размер", fontSize = 10.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text("Установить")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Скриншоты",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Описание
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Описание приложения")
            Spacer(Modifier.height(8.dp))
            Text(
                text = app.description,
                maxLines = if (descriptionCollapsed) Int.MAX_VALUE else 3
            )
            if (!descriptionCollapsed) {
                TextButton(onClick = { descriptionCollapsed = true }) {
                    Text("Читать подробнее")
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = app.developer)
                Text(text = "Разработчик", fontSize = 12.sp)
            }
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}