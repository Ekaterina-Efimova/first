package com.example.first.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.first.domain.model.App
import com.example.first.ui.components.AppBar
import com.example.first.ui.components.AppCard

private val showcaseApps = listOf(
    App("App 1", "Описание 1", "Категория", "ic_launcher_foreground"),
    App("App 2", "Описание 2", "Категория", "ic_launcher_foreground"),
    App("App 3", "Описание 3", "Категория", "ic_launcher_foreground"),
    App("App 4", "Описание 4", "Категория", "ic_launcher_foreground"),
    App("App 5", "Описание 5", "Категория", "ic_launcher_foreground"),
    App("App 6", "Описание 6", "Категория", "ic_launcher_foreground"),
    App("App 7", "Описание 7", "Категория", "ic_launcher_foreground"),
    App("App 8", "Описание 8", "Категория", "ic_launcher_foreground"),
    App("App 9", "Описание 9", "Категория", "ic_launcher_foreground")
)

@Composable
fun AppsScreen(
    onAppClick: (String) -> Unit,
    viewModel: AppsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.snackbarMessage) {
        viewModel.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onSnackbarShown()
        }
    }

    Scaffold(
        topBar = { AppBar() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when {
                // Состояние загрузки
                viewModel.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                // Состояние ошибки
                viewModel.error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center).padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = viewModel.error!!, color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadApps() }) {
                            Text("Повторить")
                        }
                    }
                }
                // Успешная загрузка (список)
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(viewModel.apps) { app ->
                            AppCard(
                                app = app,
                                onClick = { onAppClick(app.name) },
                                onLogoClick = { viewModel.onLogoClicked(app.name) }
                            )
                        }
                        // Витрина
                        item {
                            ShowcaseSection(onAppClick = onAppClick, onLogoClick = { viewModel.onLogoClicked(it) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowcaseSection(onAppClick: (String) -> Unit, onLogoClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = "Витрина",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        val pagerState = rememberPagerState(pageCount = { 3 })

        HorizontalPager(state = pagerState) { page ->
            val startIndex = page * 3
            val pageApps = showcaseApps.subList(startIndex, startIndex + 3)

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pageApps.forEach { app ->
                    AppCard(
                        app = app,
                        onClick = { onAppClick(app.name) },
                        onLogoClick = { onLogoClick(app.name) }
                    )
                }
            }
        }
    }
}