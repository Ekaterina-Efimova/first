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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.first.R
import com.example.first.ui.components.AppBar
import com.example.first.ui.components.AppCard
import com.example.first.ui.model.AppItem
import com.example.first.ui.model.SimilarApp

// Витрину пока оставим хардкоженной
private val showcaseApps = listOf(
    SimilarApp("App 1", R.drawable.ic_launcher_foreground, "Описание 1"),
    SimilarApp("App 2", R.drawable.ic_launcher_foreground, "Описание 2"),
    SimilarApp("App 3", R.drawable.ic_launcher_foreground, "Описание 3"),
    SimilarApp("App 4", R.drawable.ic_launcher_foreground, "Описание 4"),
    SimilarApp("App 5", R.drawable.ic_launcher_foreground, "Описание 5"),
    SimilarApp("App 6", R.drawable.ic_launcher_foreground, "Описание 6"),
    SimilarApp("App 7", R.drawable.ic_launcher_foreground, "Описание 7"),
    SimilarApp("App 8", R.drawable.ic_launcher_foreground, "Описание 8"),
    SimilarApp("App 9", R.drawable.ic_launcher_foreground, "Описание 9")
)

@Composable
fun AppsScreen(
    onAppClick: (String) -> Unit,
    viewModel: AppsViewModel = viewModel() // Внедряем ViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Следим за сообщением из ViewModel и показываем Snackbar
    LaunchedEffect(viewModel.snackbarMessage) {
        viewModel.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onSnackbarShown() // Сообщаем ViewModel, что показали
        }
    }

    Scaffold(
        topBar = { AppBar() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Основной список из ViewModel
            items(viewModel.sampleApps) { app ->
                AppCard(
                    app = app,
                    onClick = { onAppClick(app.name) },
                    onLogoClick = { viewModel.onLogoClicked(app.name) } // Клик по логотипу
                )
            }

            // Витрина
            item {
                ShowcaseSection(onAppClick = onAppClick, onLogoClick = { viewModel.onLogoClicked(it) })
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
                        app = AppItem(app.iconRes, app.name, app.description, "Категория"),
                        onClick = { onAppClick(app.name) },
                        onLogoClick = { onLogoClick(app.name) }
                    )
                }
            }
        }
    }
}