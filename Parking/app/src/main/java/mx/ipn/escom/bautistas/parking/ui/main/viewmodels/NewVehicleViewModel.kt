package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.user.createPartFromBitmap
import mx.ipn.escom.bautistas.parking.data.user.createPartFromString
import mx.ipn.escom.bautistas.parking.data.vehicle.VehicleRepository
import mx.ipn.escom.bautistas.parking.model.CreateVehicleResponse
import mx.ipn.escom.bautistas.parking.ui.main.interactions.VehicleState
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class NewVehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _newVehicleUIState = MutableStateFlow(VehicleState())
    val newVehicleUiState = _newVehicleUIState.asStateFlow()


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

    fun onVehicleTypeChange(type: Int) {
        vehicleTypeVal = type
        _newVehicleUIState.update {
            it.copy(
                isTypeVehicleSelected = vehicleTypeVal != 0,
                isError = false
            )
        }
    }

    fun onPlateChange(plate: String) {
        plateVal = plate
        _newVehicleUIState.update {
            it.copy(
                isPlateValid = plateVal.isNotBlank(),
                isError = false
            )
        }
    }

    fun onModelChange(model: String) {
        modelVal = model
        _newVehicleUIState.update {
            it.copy(
                isModelValid = modelVal.isNotBlank().and(modelVal.length >= 4),
                isError = false
            )
        }
    }

    fun onBrandChange(brand: String) {
        brandVal = brand
        _newVehicleUIState.update {
            it.copy(
                isBrandValid = brandVal.isNotBlank(),
                isError = false
            )
        }
    }

    fun onColorChange(color: String) {
        colorVal = color
        _newVehicleUIState.update {
            it.copy(
                isColorValid = colorVal.isNotBlank(),
                isError = false
            )
        }
    }


    fun onDocumentPhotoChange(photo: Bitmap?) {
        documentPhoto = photo
        documentPhoto?.let {
            _newVehicleUIState.update {
                it.copy(isDocumentTaken = true, isError = false)
            }
        }
    }

    private suspend fun createVehicle(
        vehicleType: RequestBody,
        brand: RequestBody,
        model: RequestBody,
        placa: RequestBody,
        color: RequestBody,
        document: MultipartBody.Part
    ): Result<CreateVehicleResponse> {
        return try {
            val response = vehicleRepository.createVehicle(
                vehicleType,
                brand,
                model,
                placa,
                color,
                document,
            )
            Result.success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            _newVehicleUIState.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                    message = errorBody ?: "Error desconocido"
                )
            }
            Result.failure(e)
        } catch (e: IOException) {
            _newVehicleUIState.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                    message = e.message ?: "Error desconocido"
                )
            }
            Result.failure(e)
        }
    }


    fun onNewVehicleCreated() {
        _newVehicleUIState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val vehicleType = createPartFromString(vehicleTypeVal.toString())
            val plate = createPartFromString(plateVal)
            val model = createPartFromString(modelVal)
            val brand = createPartFromString(brandVal)
            val color = createPartFromString(colorVal)
            val document = createPartFromBitmap(documentPhoto!!, "documento")

            val result = createVehicle(
                vehicleType!!,
                brand!!,
                model!!,
                plate!!,
                color!!,
                document,
            )

            if (result.isSuccess) {
                _newVehicleUIState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        message = "¡Vehículo creado correctamente!"
                    )

                }
            }

        }
    }


}