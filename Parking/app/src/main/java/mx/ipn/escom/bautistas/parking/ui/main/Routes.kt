package mx.ipn.escom.bautistas.parking.ui.main

sealed class Routes(
    val route: String
) {
    data object SplashScreen : Routes(route = "/")
    data object LoginScreen : Routes(route = "/login")
    data object HomeScreen : Routes(route = "/home")
    data object GenerateAccessCard : Routes(route = "/generate_access_card")
    data object NewAccountUser : Routes(route = "/new_account_user")
    data object NewVehicle : Routes(route = "/new_vehicle")
    data object NewUser : Routes(route = "/new_user")

    //  newUserScreen
    data object NewAccountUserMainContent : Routes(route = "/new_account_user_main")
    data object NewUserMainContent : Routes(route = "/new_user_main")
    data object NewUserCamaraP : Routes(route = "/camara_person")
    data object NewUserCamaraI : Routes(route = "/camara_ID")

    //    newVehicleScreen
    data object NewVehicleMainContent : Routes(route = "/new_vehicle_main")

    //    generateAccessCard
    data object GenAccessCardMain : Routes(route = "/generate_access_card_main")
    data object CompleteUserInfo : Routes(route = "/completeUser")

    //    Manual registration
    data object ManualRegistration : Routes(route = "/manual_registration_main")
    data object ManualRegistrationSelect : Routes(route = "/manual_registration_select")
    data object ManualRegistrationVehicle : Routes(route = "/manual_registration_vehicle")
    data object ManualRegistrationResult : Routes(route = "/manual_registration_result")

    //    Incidents
    data object NewIncident : Routes(route = "/new_incident")
    data object IncidentDetail : Routes(route = "/incident_detail")
}