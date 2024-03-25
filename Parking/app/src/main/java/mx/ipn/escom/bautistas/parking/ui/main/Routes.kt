package mx.ipn.escom.bautistas.parking.ui.main

sealed class Routes(
    val route: String
) {
    data object SplashScreen : Routes(route = "/")
    data object LoginScreen : Routes(route = "/login")
    data object HomeScreen : Routes(route = "/home")
}