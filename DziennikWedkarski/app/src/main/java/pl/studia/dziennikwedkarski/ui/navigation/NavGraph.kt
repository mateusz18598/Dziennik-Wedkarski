package pl.studia.dziennikwedkarski.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import pl.studia.dziennikwedkarski.ui.list.EntryListScreen
import pl.studia.dziennikwedkarski.ui.add.AddEntryScreen
import pl.studia.dziennikwedkarski.viewmodel.FishingEntryViewModel
import pl.studia.dziennikwedkarski.ui.details.DetailsScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.foundation.clickable



@Composable
fun NavGraph(viewModel: FishingEntryViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.LIST
    ) {
        composable(NavRoutes.LIST) {
            EntryListScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate(NavRoutes.ADD) },
                onItemClick = { id ->
                    navController.navigate("${NavRoutes.DETAILS}/$id")
                },
                onStatisticsClick = {
                    navController.navigate(NavRoutes.STATISTICS)
                }
            )


        }

        composable(NavRoutes.ADD) {
            AddEntryScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }



        composable(
            route = "${NavRoutes.DETAILS}/{entryId}",
            arguments = listOf(navArgument("entryId") { type = NavType.LongType })
        ) { backStackEntry ->

            val entryId = backStackEntry.arguments?.getLong("entryId") ?: return@composable

            DetailsScreen(
                viewModel = viewModel,
                entryId = entryId,
                onBack = { navController.popBackStack() },
                onEdit = { id ->
                    navController.navigate("${NavRoutes.EDIT}/$id")
                }
            )

        }

        composable(
            route = "${NavRoutes.EDIT}/{entryId}",
            arguments = listOf(navArgument("entryId") { type = NavType.LongType })
        ) { backStackEntry ->

            val entryId = backStackEntry.arguments?.getLong("entryId") ?: return@composable

            AddEntryScreen(
                viewModel = viewModel,
                entryId = entryId,
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }


        composable(NavRoutes.STATISTICS) {
            pl.studia.dziennikwedkarski.ui.statistics.StatisticsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

    }
}
