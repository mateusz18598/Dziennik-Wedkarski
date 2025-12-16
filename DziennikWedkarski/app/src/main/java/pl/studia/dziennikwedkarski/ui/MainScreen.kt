/*
package pl.studia.dziennikwedkarski.ui

import androidx.compose.runtime.*
import pl.studia.dziennikwedkarski.viewmodel.FishingEntryViewModel
import pl.studia.dziennikwedkarski.ui.list.EntryListScreen
import pl.studia.dziennikwedkarski.ui.details.DetailsScreen
import pl.studia.dziennikwedkarski.ui.statistics.StatisticsScreen

@Composable
fun MainScreen(viewModel: FishingEntryViewModel) {

    var currentScreen by remember { mutableStateOf<Screen>(Screen.List) }
    var selectedEntryId by remember { mutableStateOf<Long?>(null) }

    when (currentScreen) {

        Screen.List -> EntryListScreen(
            viewModel = viewModel,
            onAddClick = { currentScreen = Screen.Add },
            onItemClick = {
                selectedEntryId = it
                currentScreen = Screen.Details
            },
            onStatisticsClick = {
                currentScreen = Screen.Statistics
            }
        )

        Screen.Details -> DetailsScreen(
            viewModel = viewModel,
            entryId = selectedEntryId!!,
            onBack = { currentScreen = Screen.List },
            onEdit = { */
/* later *//*
 }
        )

        Screen.Statistics -> StatisticsScreen(
            viewModel = viewModel,
            onBack = { currentScreen = Screen.List }
        )

        Screen.Add -> {
            */
/*  *//*

        }
    }
}
*/
