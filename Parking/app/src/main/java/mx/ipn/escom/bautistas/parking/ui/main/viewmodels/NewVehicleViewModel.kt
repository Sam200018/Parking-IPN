package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.ipn.escom.bautistas.parking.data.vehicle.VehicleRepository
import javax.inject.Inject


@HiltViewModel
class NewVehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    var vehicleTypeVal: Int by mutableIntStateOf(0)
        private set

    var plateVal: String by mutableStateOf("")
        private set

    var modelVal: String by mutableStateOf("")
        private set

    var brandVal: String by mutableStateOf("")
        private set

    var colorVal: String by mutableStateOf("")
        private set

    var documentPhoto: Bitmap? by mutableStateOf(null)
        private set

    fun onDocumentPhotoChange(photo: Bitmap?) {
        documentPhoto = photo
    }


}