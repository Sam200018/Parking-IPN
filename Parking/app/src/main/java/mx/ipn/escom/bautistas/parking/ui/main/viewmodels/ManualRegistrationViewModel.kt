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
import mx.ipn.escom.bautistas.parking.data.auth.AuthRepository
import mx.ipn.escom.bautistas.parking.data.records.RecordsRepository
import mx.ipn.escom.bautistas.parking.data.user.UserRepository
import mx.ipn.escom.bautistas.parking.data.vehicle.VehicleRepository
import mx.ipn.escom.bautistas.parking.model.InfoRecordRequest
import mx.ipn.escom.bautistas.parking.model.InfoRecordResponse
import mx.ipn.escom.bautistas.parking.model.RegisterMovRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import mx.ipn.escom.bautistas.parking.ui.main.interactions.ManualRegistrationState
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.Movement
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ManualRegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val vehicleRepository: VehicleRepository,
    private val recordsRepository: RecordsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _manualRegistrationState = MutableStateFlow(ManualRegistrationState())
    val manualRegistrationState = _manualRegistrationState.asStateFlow()

    init {
        viewModelScope.launch {
            var peopleResponse = userRepository.getPeople()
            var vehiclesResponse = vehicleRepository.getAllVehicles(true)

            _manualRegistrationState.update {
                it.copy(
                    peopleList = peopleResponse.usuarios,
                    vehicleList = vehiclesResponse.vehicles
                )
            }
        }
    }


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

        viewModelScope.launch {
            val peopleResponse = userRepository.getPeople(input = input)

            _manualRegistrationState.update {
                it.copy(
                    peopleList = peopleResponse.usuarios
                )
            }
        }
    }

    fun onSearchVehicleInputChanged(input: String) {
        searchVehicle = input
        viewModelScope.launch {
            val vehiclesResponse = vehicleRepository.getAllVehicles(true, input)
            _manualRegistrationState.update {
                it.copy(
                    vehicleList = vehiclesResponse.vehicles
                )
            }
        }
    }

    fun onPersonSelected(id: Long) {
        personIdSelected = id
    }

    fun onVehicleSelected(id: Long) {
        vehicleIdSelected = id
    }

    fun manualRegistrationRequest(
        navToResult: () -> Unit
    ) {
        Log.i("PersonId", personIdSelected.toString())
        Log.i("VehicleId", vehicleIdSelected.toString())

        _manualRegistrationState.update {
            it.copy(
                isLoading = true,
            )
        }

        val infoRecordRequest = InfoRecordRequest(
            personId = personIdSelected,
            vehicleId = vehicleIdSelected
        )
        viewModelScope.launch {

            getInfoRecord(infoRecordRequest).onSuccess { resp: InfoRecordResponse ->
                val movement = when (resp.movement) {
                    0 -> Movement.CheckOut
                    1 -> Movement.CheckIn
                    else -> Movement.Unknown
                }

                if (resp.accessCard != null) {
                    _manualRegistrationState.update {
                        it.copy(
                            accessCard = resp.accessCard,
                            movement = movement,
                            isLoading = false,
                            isSuccess = true,
                            note = resp.note
                        )
                    }
                } else if (resp.visit != null) {
                    _manualRegistrationState.update {
                        it.copy(
                            visit = resp.visit,
                            movement = movement,
                            isLoading = false,
                            isSuccess = true,
                            note = resp.note
                        )
                    }
                }


                navToResult()
            }.onFailure { error: Throwable ->
                _manualRegistrationState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = error.message ?: "Error desconocido"
                    )
                }
            }

        }
    }

    private suspend fun getInfoRecord(infoRecordRequest: InfoRecordRequest): Result<InfoRecordResponse> {
        return try {
            val resp = recordsRepository.getInfoToRecord(infoRecordRequest)
            Result.success(resp)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    fun onRegisterMov() {
        _manualRegistrationState.update {
            it.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            val authInfo = authRepository.getToken()
            val idAccount = authInfo!!.id

            val movement = when (manualRegistrationState.value.movement) {
                Movement.Unknown -> 2
                Movement.CheckIn -> 1
                Movement.CheckOut -> 0
            }

            val idAccessCard = manualRegistrationState.value.accessCard!!.idTarjetaAcceso
            val visitId = manualRegistrationState.value.visit!!.visitId


            val registerMovRequest = RegisterMovRequest(
                idCuenta = idAccount.toLong(),
                movement = movement,
                idTarjetaAcceso = idAccessCard,
                idVisita = visitId
            )

            Log.i("Register Mov R", registerMovRequest.toString())

            registerMov(registerMovRequest).onSuccess { resp ->
                _manualRegistrationState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        message = resp.message
                    )
                }
            }.onFailure { error ->
                _manualRegistrationState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = error.message ?: "Error desconocido"
                    )
                }
            }

        }

    }

    private suspend fun registerMov(registerMovRequest: RegisterMovRequest): Result<SimpleResponse> {
        return try {
            val resp = recordsRepository.registerMov(registerMovRequest)
            Result.success(resp)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: java.io.IOException) {
            Result.failure(e)

        }
    }


}