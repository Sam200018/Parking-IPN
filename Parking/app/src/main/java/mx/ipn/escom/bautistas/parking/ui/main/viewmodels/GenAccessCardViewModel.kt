package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.ipn.escom.bautistas.parking.data.card.AccessCardRepository
import mx.ipn.escom.bautistas.parking.data.user.UserRepository
import mx.ipn.escom.bautistas.parking.data.vehicle.VehicleRepository
import mx.ipn.escom.bautistas.parking.model.CheckAccountResponse
import mx.ipn.escom.bautistas.parking.model.GenAccessCardRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import mx.ipn.escom.bautistas.parking.ui.main.interactions.GenAccessCardState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GenAccessCardViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val vehicleRepository: VehicleRepository,
    private val accessCardRepository: AccessCardRepository,
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

    fun onGenAccessCard(
        goToCompleteInfo: () -> Unit,
    ) {
        _genAccessCardState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            isVerifyUser().onSuccess { check ->

                if (check.verified) {
                    genAccessCard().onSuccess { resp: SimpleResponse ->

                        _genAccessCardState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = true,
                                message = resp.message
                            )

                        }
                    }.onFailure { genAccessCardFailure ->
                        _genAccessCardState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                message = genAccessCardFailure.message ?: "Error desconocido"
                            )
                        }
                    }
                } else {
                    withContext(Dispatchers.Main){
                        _genAccessCardState.update {
                            it.copy(
                                isLoading = false,
                                message = check.message
                            )
                        }
                        goToCompleteInfo()
                    }

                }
            }.onFailure { error ->

                _genAccessCardState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = error.message ?: "Error desconocido"
                    )
                }

            }

        }
    }

    private suspend fun isVerifyUser(): Result<CheckAccountResponse> {
        Log.i("Verifying user...", "...")
        return try {
            val resp = userRepository.checkAccountByPersona(personIdSelected)
            Result.success(resp)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    private suspend fun genAccessCard(): Result<SimpleResponse> {
        return try {

            val accessCardRequest = GenAccessCardRequest(
                personIdSelected,
                vehicleIdSelected
            )
            val res = accessCardRepository.genAccessCard(accessCardRequest)
            Result.success(res)

        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}