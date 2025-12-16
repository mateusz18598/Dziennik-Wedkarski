package pl.studia.dziennikwedkarski.ui.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.studia.dziennikwedkarski.data.local.entity.FishingEntryEntity
import pl.studia.dziennikwedkarski.viewmodel.FishingEntryViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(
    viewModel: FishingEntryViewModel,
    entryId: Long? = null,
    onBack: () -> Unit,
    onSaved: () -> Unit
) {

    // --- FORM STATE ---
    var date by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    var location by remember { mutableStateOf("") }
    var fishingType by remember { mutableStateOf("") }
    var weather by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var durationHours by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val entry by viewModel.selectedEntry.collectAsState()

    // --- LOAD ENTRY ---
    LaunchedEffect(entryId) {
        if (entryId != null) {
            viewModel.loadEntry(entryId)
        }
    }

    LaunchedEffect(entry) {
        entry?.let {
            date = LocalDate.parse(it.date)
            location = it.location
            fishingType = it.fishingType
            weather = it.weather
            temperature = it.temperature.toString()
            durationHours = it.durationHours.toString()
            notes = it.notes
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (entryId == null) "Dodaj wpis 🎣" else "Edytuj wpis 🎣")
                },
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // --- DATE FIELD ---
            val interactionSource = remember { MutableInteractionSource() }

            OutlinedTextField(
                value = date.toString(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Data połowu") },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Wybierz datę"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )


            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Łowisko") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fishingType,
                onValueChange = { fishingType = it },
                label = { Text("Metoda") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = weather,
                onValueChange = { weather = it },
                label = { Text("Pogoda") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = temperature,
                onValueChange = { temperature = it },
                label = { Text("Temperatura (°C)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = durationHours,
                onValueChange = { durationHours = it },
                label = { Text("Czas łowienia (h)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notatki") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (entryId == null) {
                        viewModel.addEntry(
                            FishingEntryEntity(
                                date = date.toString(),
                                temperature = temperature.toIntOrNull() ?: 0,
                                weather = weather,
                                pressure = 0,
                                location = location,
                                fishingType = fishingType,
                                durationHours = durationHours.toIntOrNull() ?: 0,
                                notes = notes
                            )
                        )
                    } else {
                        entry?.let {
                            viewModel.updateEntry(
                                it.copy(
                                    date = date.toString(),
                                    location = location,
                                    fishingType = fishingType,
                                    weather = weather,
                                    temperature = temperature.toIntOrNull() ?: it.temperature,
                                    durationHours = durationHours.toIntOrNull() ?: it.durationHours,
                                    notes = notes
                                )
                            )
                        }
                    }
                    onSaved()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (entryId == null) "Dodaj" else "Zapisz zmiany")
            }
        }

        // --- DATE PICKER ---
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = date
                    .atStartOfDay()
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli()
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            date = java.time.Instant
                                .ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Anuluj")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
