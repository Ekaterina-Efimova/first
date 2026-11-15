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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.first.domain.model.Category
import com.example.first.domain.model.InstallState
import kotlin.math.roundToInt

@Composable
fun AppDetailScreen(
    appName: String,
    navController: NavController,
    viewModel: AppDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(appName) { viewModel.loadApp(appName) }

    val state by viewModel.state.collectAsState()

    when (val s = state) {
        is AppDetailsState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is AppDetailsState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(s.message, color = MaterialTheme.colorScheme.error)
            }
        }
        is AppDetailsState.Content -> {
            AppDetailContent(
                app = s.app,
                installState = s.installState,
                onBack = { navController.popBackStack() },
                onInstallClick = { viewModel.onInstallClick() }
            )
        }
    }
}

@Composable
private fun AppDetailContent(
    app: com.example.first.domain.model.AppDetails,
    installState: InstallState,
    onBack: () -> Unit,
    onInstallClick: () -> Unit
) {
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
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад",
                    tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.Share, "Поделиться",
                    tint = MaterialTheme.colorScheme.primary)
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
                modifier = Modifier.size(128.dp).clip(RoundedCornerShape(16.dp)).padding(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = if (app.category == Category.GAME) "Игры" else "Приложения",
                    color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(app.name, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(4.dp))
                Text(app.developer, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Row {
                    Column {
                        Text("${app.ageRating}+")
                        Text("Возраст", fontSize = 10.sp)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("${app.size.roundToInt()} MB")
                        Text("Размер", fontSize = 10.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        InstallButton(installState = installState, onInstallClick = onInstallClick)

        Spacer(Modifier.height(16.dp))

        Text("Скриншоты", modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier.weight(1f).height(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Описание приложения")
            Spacer(Modifier.height(8.dp))
            Text(app.description,
                maxLines = if (descriptionCollapsed) Int.MAX_VALUE else 3)
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
                Text(app.developer)
                Text("Разработчик", fontSize = 12.sp)
            }
            IconButton(onClick = { }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null)
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun InstallButton(
    installState: InstallState,
    onInstallClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (installState) {
            is InstallState.NotInstalled -> {
                Button(
                    onClick = onInstallClick,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Установить") }
            }
            is InstallState.Installing -> {
                Button(
                    onClick = onInstallClick,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Отменить (${installState.progress}%)") }
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { installState.progress / 100f },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is InstallState.Installed -> {
                Button(
                    onClick = { },
                    enabled = false,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Установлено") }
            }
        }
    }
}