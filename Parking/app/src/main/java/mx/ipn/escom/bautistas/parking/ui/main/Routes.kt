package mx.ipn.escom.bautistas.parking.ui.main

sealed class Routes(
    val route: String
) {
    data object SplashScreen : Routes(route = "/")
    data object LoginScreen : Routes(route = "/login")
    data object HomeScreen : Routes(route = "/home")
    data object GenerateAccessCard : Routes(route = "/generate_access_card")
    data object NewUser : Routes(route = "/new_user")
    data object NewVehicle : Routes(route = "/new_vehicle")

    //  newUserScreen
    data object NewUserMainContent : Routes(route = "/new_user_main")
    data object NewUserCamaraP : Routes(route = "/camara_person")
    data object NewUserCamaraI : Routes(route = "/camara_ID")

//    newVehicleScreen
    data object NewVehicleMainContent : Routes(route = "/new_vehicle_main")


}