package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.user.UserRepository
import mx.ipn.escom.bautistas.parking.data.vehicle.VehicleRepository
import mx.ipn.escom.bautistas.parking.ui.main.interactions.GenAccessCardState
import javax.inject.Inject

@HiltViewModel
class GenAccessCardViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _genAccessCardState = MutableStateFlow(GenAccessCardState())
    val genAccessCardState = _genAccessCardState.asStateFlow()

    init {
        viewModelScope.launch {
            var peopleResponse = userRepository.getPeople()
            Log.i("people", peopleResponse.toString())
            var vehiclesResponse = vehicleRepository.getAllVehicles(asignado = false)
            Log.i("vehicle", vehiclesResponse.toString())

            _genAccessCardState.update {
                it.copy(
                    peopleList = peopleResponse.usuarios,
                    vehicleList = vehiclesResponse.vehicles
                )
            }
        }
    }

    var searchPersonInput: String by mutableStateOf("")
        private set

    var searchVehicleInput: String by mutableStateOf("")
        private set

    var personIdSelected: Long by mutableLongStateOf(0)
        private set

    var vehicleIdSelected: Long by mutableLongStateOf(0)
        private set

    fun personIdSelectedChanged(idSelected: Long) {
        personIdSelected = idSelected
    }

    fun vehicleIdSelectedChange(idSelected: Long) {
        vehicleIdSelected = idSelected
    }

    fun searchPersonChanged(searchInput: String) {
        searchPersonInput = searchInput
        viewModelScope.launch {
            val peopleResponse =
                userRepository.getPeople(input = searchInput)

            _genAccessCardState.update {
                it.copy(
                    peopleList = peopleResponse.usuarios
                )
            }
        }
    }

    fun searchVehicleChanged(searchInput: String) {
        searchVehicleInput = searchInput

        viewModelScope.launch {
            val vehiclesResponse =
                vehicleRepository.getAllVehicles(asignado = false, placa = searchInput)

            _genAccessCardState.update {
                it.copy(
                    vehicleList = vehiclesResponse.vehicles
                )
            }
        }
    }

}

