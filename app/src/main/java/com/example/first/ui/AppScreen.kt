package com.example.first.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.first.ui.components.AppBar
import com.example.first.ui.components.AppCard

@Composable
fun AppsScreen(
    onAppClick: (String) -> Unit,
    viewModel: AppsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.snackbarMessage) {
        viewModel.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onSnackbarShown()
        }
    }

    Scaffold(
        topBar = { AppBar() },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            when {
                viewModel.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                viewModel.error != null -> Column(
                    Modifier.align(Alignment.Center).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(viewModel.error!!, color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadApps() }) { Text("Повторить") }
                }

                else -> Column {
                    // Кнопки для запуска замеров
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.loadRatingsSequentially() },
                            enabled = !viewModel.ratingsLoading,
                            modifier = Modifier.weight(1f)
                        ) { Text("Последовательно") }
                        Button(
                            onClick = { viewModel.loadRatingsInParallel() },
                            enabled = !viewModel.ratingsLoading,
                            modifier = Modifier.weight(1f)
                        ) { Text("Параллельно") }
                    }

                    // Результат замера
                    viewModel.lastMeasure?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (viewModel.ratingsLoading) {
                        LinearProgressIndicator(Modifier.fillMaxWidth())
                    }

                    LazyColumn(Modifier.fillMaxSize()) {
                        items(viewModel.apps) { app ->
                            AppCardWithRating(
                                app = app,
                                rating = viewModel.ratings[app.name],
                                onClick = { onAppClick(app.name) },
                                onLogoClick = { viewModel.onLogoClicked(app.name) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AppCardWithRating(
    app: com.example.first.domain.model.App,
    rating: Float?,
    onClick: () -> Unit,
    onLogoClick: () -> Unit
) {
    Column {
        com.example.first.ui.components.AppCard(
            app = app,
            onClick = onClick,
            onLogoClick = onLogoClick
        )
        rating?.let {
            Text(
                text = "★ ${"%.1f".format(it)}",
                modifier = Modifier.padding(start = 76.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}