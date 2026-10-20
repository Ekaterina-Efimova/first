package com.example.first.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.first.ui.model.AppItem

@Composable
fun AppCard(
    app: AppItem,
    onClick: () -> Unit = {},
    onLogoClick: () -> Unit = {} // Добавили колбэк для логотипа
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = app.iconRes),
                contentDescription = app.name,
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onLogoClick() } // Слушатель клика на иконке
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(text = app.name, style = MaterialTheme.typography.titleMedium)
                Text(text = app.description, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = app.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}