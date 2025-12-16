package pl.studia.dziennikwedkarski.ui

sealed class Screen {
    object List : Screen()
    object Details : Screen()
    object Add : Screen()
    object Statistics : Screen()
}
