package mx.ipn.escom.bautistas.parking.ui.main.interactions

import mx.ipn.escom.bautistas.parking.model.AccessCard
import mx.ipn.escom.bautistas.parking.model.InfoRecordResponse
import mx.ipn.escom.bautistas.parking.model.Persona
import mx.ipn.escom.bautistas.parking.model.Vehicle
import mx.ipn.escom.bautistas.parking.model.Visit
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.Movement

data class ManualRegistrationState(
    val peopleList: List<Persona> = listOf(),
    val vehicleList: List<Vehicle> = listOf(),
    val isPersonSelectedIncomplete: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val accessCard: AccessCard? = null,
    val visit: Visit? = null,
    val movement: Movement = Movement.Unknown,
    val note: String = "",
)
