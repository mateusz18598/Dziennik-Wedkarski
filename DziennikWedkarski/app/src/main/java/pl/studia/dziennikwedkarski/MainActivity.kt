package pl.studia.dziennikwedkarski

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.studia.dziennikwedkarski.ui.list.EntryListScreen
import pl.studia.dziennikwedkarski.ui.theme.DziennikWedkarskiTheme
import pl.studia.dziennikwedkarski.viewmodel.FishingEntryViewModel
import pl.studia.dziennikwedkarski.ui.navigation.NavGraph
import androidx.lifecycle.viewmodel.compose.viewModel
/*import pl.studia.dziennikwedkarski.ui.MainScreen*/
import pl.studia.dziennikwedkarski.ui.navigation.NavGraph



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DziennikWedkarskiTheme {
                val viewModel: FishingEntryViewModel = viewModel()
                NavGraph(viewModel)
            }
        }

    }
}
