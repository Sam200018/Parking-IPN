package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.ipn.escom.bautistas.parking.ui.main.interactions.ManualRegistrationState
import javax.inject.Inject

@HiltViewModel
class ManualRegistrationViewModel @Inject constructor() : ViewModel() {

    private val _manualRegistrationState = MutableStateFlow(ManualRegistrationState())
    val manualRegistrationState = _manualRegistrationState.asStateFlow()

    var searchPersonInput: String by mutableStateOf("")
        private set

    var searchVehicle: String by mutableStateOf("")
        private set

    var personIdSelected: Long by mutableLongStateOf(0)
        private set

    var vehicleIdSelected: Long by mutableLongStateOf(0)
        private set

    fun onSearchPersonInputChanged(input: String) {
        searchPersonInput = input
    }

    fun onSearchVehicleInputChanged(input: String) {
        searchVehicle = input
    }

    fun onPersonSelected(id: Long) {
        personIdSelected = id
    }

    fun onVehicleSelected(id: Long) {
        vehicleIdSelected = id
    }

    fun saveManualRegistration(
        navToResult: () -> Unit
    ) {

    }

}