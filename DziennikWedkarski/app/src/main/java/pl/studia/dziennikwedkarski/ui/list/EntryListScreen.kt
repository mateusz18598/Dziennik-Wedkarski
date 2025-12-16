package pl.studia.dziennikwedkarski.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.studia.dziennikwedkarski.data.local.entity.FishingEntryEntity
import pl.studia.dziennikwedkarski.viewmodel.FishingEntryViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.ui.Alignment



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryListScreen(
    viewModel: FishingEntryViewModel,
    onAddClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onStatisticsClick: () -> Unit
) {
    val entries by viewModel.entries.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Dziennik Wędkarski",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onAddClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Dodaj wpis")
                }

                Button(
                    onClick = onStatisticsClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Statystyki")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (entries.isEmpty()) {
                Text("Brak wpisów – dodaj pierwszy połów")
            } else {
                LazyColumn {
                    items(entries) { entry ->
                        EntryItem(
                            entry = entry,
                            onClick = { onItemClick(entry.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EntryItem(
    entry: FishingEntryEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(entry.date, style = MaterialTheme.typography.titleMedium)
            Text("Łowisko: ${entry.location}")
            Text("Metoda: ${entry.fishingType}")
            Text("Czas: ${entry.durationHours} h")
            Text("Pogoda: ${entry.weather}, ${entry.temperature}°C")

            if (entry.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Notatki: ${entry.notes}")
            }
        }
    }
}
