package mx.ipn.escom.bautistas.parking.ui.main.interactions

import mx.ipn.escom.bautistas.parking.model.Persona
import mx.ipn.escom.bautistas.parking.model.Vehicle

data class ManualRegistrationState(
    val peopleList: List<Persona> = listOf(),
    val vehicleList: List<Vehicle> = listOf(),
    val isPersonSelectedIncomplete: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val message: String = ""
)
