package mx.ipn.escom.bautistas.parking.ui.main

sealed class Routes(
    val route: String
) {
    data object SplashScreen : Routes(route = "/")
    data object LoginScreen : Routes(route = "/login")
    data object HomeScreen : Routes(route = "/home")
    data object UserSelect : Routes(route = "/user_select")
    data object NewUser : Routes(route = "/new_user")
    data object UserAvailable : Routes(route = "/user_available")
}