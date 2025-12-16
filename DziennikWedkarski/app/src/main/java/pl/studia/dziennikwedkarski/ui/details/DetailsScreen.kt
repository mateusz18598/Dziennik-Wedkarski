package pl.studia.dziennikwedkarski.ui.details

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pl.studia.dziennikwedkarski.data.local.entity.PhotoEntity
import pl.studia.dziennikwedkarski.viewmodel.FishingEntryViewModel
import pl.studia.dziennikwedkarski.data.local.entity.FishCatchEntity
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun DetailsScreen(
    viewModel: FishingEntryViewModel,
    entryId: Long,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit
) {
    val entry by viewModel.selectedEntry.collectAsState()
    val photos by viewModel.photos.collectAsState()
    val fish by viewModel.fish.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var photoToDelete by remember { mutableStateOf<PhotoEntity?>(null) }
    var showAddFishDialog by remember { mutableStateOf(false) }
    var fishToDelete by remember { mutableStateOf<FishCatchEntity?>(null) }
    var selectedFishForSizes by remember { mutableStateOf<FishCatchEntity?>(null) }

    LaunchedEffect(entryId) {
        viewModel.loadEntry(entryId)
        viewModel.loadPhotos(entryId)
        viewModel.loadFish(entryId)
    }

    val pickImageLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let { viewModel.addPhoto(entryId, it.toString()) }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Szczegóły wpisu") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Cofnij"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onEdit(entryId) }) {
                        Text("✏️")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Text("🗑️")
                    }
                }
            )

        }
    ) { padding ->

        if (entry == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            // --- BASIC INFO ---
            Text(entry!!.date, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text("Łowisko: ${entry!!.location}")
            Text("Metoda: ${entry!!.fishingType}")
            Text("Pogoda: ${entry!!.weather}")
            Text("Temperatura: ${entry!!.temperature} °C")
            Text("Czas łowienia: ${entry!!.durationHours} h")

            Spacer(Modifier.height(16.dp))

            // --- PHOTOS ---
            if (photos.isNotEmpty()) {
                Text("Zdjęcia:", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                LazyRow {
                    items(photos) { photo ->
                        Image(
                            painter = rememberAsyncImagePainter(photo.uri),
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                                .padding(end = 8.dp)
                                .clickable { photoToDelete = photo }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            Button(onClick = { pickImageLauncher.launch("image/*") }) {
                Text("Dodaj zdjęcie 📸")
            }

            Spacer(Modifier.height(16.dp))

            // --- FISH ---
            Text("Złowione ryby:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            if (fish.isEmpty()) {
                Text("Brak zapisanych ryb")
            } else {
                fish.forEach { f ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .combinedClickable(
                                onClick = {
                                    selectedFishForSizes = f
                                    viewModel.loadFishSizes(f.id)
                                },
                                onLongClick = {
                                    fishToDelete = f
                                }
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(f.species)
                        Text("${f.count} szt.")
                    }
                }
            }


            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { showAddFishDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dodaj rybę 🐟")
            }

            Spacer(Modifier.height(16.dp))

            // --- NOTES ---
            Text("Notatki:", style = MaterialTheme.typography.titleMedium)
            Text(entry!!.notes.ifBlank { "Brak notatek" })
        }
    }

    // --- DIALOGI ---
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Usuń wpis") },
            text = { Text("Czy na pewno chcesz usunąć ten wpis?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteEntry(entry!!)
                    showDeleteDialog = false
                    onBack()
                }) { Text("Usuń") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Anuluj") }
            }
        )
    }

    photoToDelete?.let { photo ->
        AlertDialog(
            onDismissRequest = { photoToDelete = null },
            title = { Text("Usuń zdjęcie") },
            text = { Text("Czy na pewno chcesz usunąć to zdjęcie?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deletePhoto(photo)
                    photoToDelete = null
                }) { Text("Usuń") }
            },
            dismissButton = {
                TextButton(onClick = { photoToDelete = null }) { Text("Anuluj") }
            }
        )
    }

    fishToDelete?.let { fish ->
        AlertDialog(
            onDismissRequest = { fishToDelete = null },
            title = { Text("Usuń rybę") },
            text = {
                Text("Czy na pewno chcesz usunąć ${fish.species} (${fish.count} szt.)?")
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteFish(fish)
                    fishToDelete = null
                }) {
                    Text("Usuń")
                }
            },
            dismissButton = {
                TextButton(onClick = { fishToDelete = null }) {
                    Text("Anuluj")
                }
            }
        )
    }

    if (showAddFishDialog) {
        var species by remember { mutableStateOf("") }
        var count by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddFishDialog = false },
            title = { Text("Dodaj rybę") },
            text = {
                Column {
                    OutlinedTextField(species, { species = it }, label = { Text("Gatunek") })
                    OutlinedTextField(count, { count = it }, label = { Text("Ilość") })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val c = count.toIntOrNull()
                    if (!species.isBlank() && c != null && c > 0) {
                        viewModel.addFish(entryId, species, c)
                    }
                    showAddFishDialog = false
                }) { Text("Dodaj") }
            },
            dismissButton = {
                TextButton(onClick = { showAddFishDialog = false }) { Text("Anuluj") }
            }
        )
    }

    selectedFishForSizes?.let { fish ->
        val sizes by viewModel.fishSizes.collectAsState()
        var newSize by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { selectedFishForSizes = null },
            title = { Text("Rozmiary – ${fish.species}") },
            text = {
                Column {
                    if (sizes.isEmpty()) {
                        Text("Brak zapisanych rozmiarów")
                    } else {
                        sizes.forEach { size ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${size.lengthCm} cm")
                                TextButton(onClick = {
                                    viewModel.deleteFishSize(size)
                                }) {
                                    Text("Usuń")
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newSize,
                        onValueChange = { newSize = it },
                        label = { Text("Nowy rozmiar (cm)") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val cm = newSize.toIntOrNull()
                    if (cm != null && cm > 0) {
                        viewModel.addFishSize(fish.id, cm)
                        newSize = ""
                    }
                }) {
                    Text("Dodaj")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedFishForSizes = null }) {
                    Text("Zamknij")
                }
            }
        )
    }

}
