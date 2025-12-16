package pl.studia.dziennikwedkarski.ui.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.studia.dziennikwedkarski.viewmodel.FishingEntryViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    viewModel: FishingEntryViewModel,
    onBack: () -> Unit
) {
    val maxSize by viewModel.maxFishSize.collectAsState()
    val avgSize by viewModel.avgFishSize.collectAsState()
    val totalCount by viewModel.totalFishCount.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadStatistics()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statystyki połowów 📊") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Cofnij"
                        )
                    }
                }
            )

        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            StatCard(
                title = "🎯 Rekord długości",
                value = "$maxSize cm"
            )

            StatCard(
                title = "📐 Średnia długość",
                value = String.format("%.1f cm", avgSize)
            )

            StatCard(
                title = "🐟 Wszystkie ryby",
                value = "$totalCount szt."
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium)
        }
    }
}
