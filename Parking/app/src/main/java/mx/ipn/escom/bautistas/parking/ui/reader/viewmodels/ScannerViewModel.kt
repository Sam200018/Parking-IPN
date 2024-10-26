package mx.ipn.escom.bautistas.parking.ui.reader.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.auth.AuthRepository
import mx.ipn.escom.bautistas.parking.data.card.AccessCardRepository
import mx.ipn.escom.bautistas.parking.data.records.RecordsRepository
import mx.ipn.escom.bautistas.parking.model.CardInfoTokeResponse
import mx.ipn.escom.bautistas.parking.model.RegisterMovRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.InfoStatus
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.Movement
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.ScannerCardState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val accessCardRepository: AccessCardRepository,
    private val recordsRepository: RecordsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _scannerUiState = MutableStateFlow(ScannerCardState())
    val scannerUiState = _scannerUiState.asStateFlow()

    fun scannerCardToken(cardToken: String) {
        _scannerUiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            getCardInfoByToken(cardToken).onSuccess { resp ->
                val movement = when (resp.movement) {
                    0 -> Movement.CheckOut
                    1 -> Movement.CheckIn
                    else -> Movement.Unknown
                }

                _scannerUiState.update {
                    ScannerCardState(
                        accessCard = resp.accessCard,
                        movement = movement
                    )
                }
            }.onFailure { error ->
                _scannerUiState.update {
                    ScannerCardState(
                        isError = true,
                        message = error.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    private suspend fun getCardInfoByToken(cardToken: String): Result<CardInfoTokeResponse> {
        return try {
            val resp = accessCardRepository.getInfoCard(cardToken = cardToken)
            Result.success(resp)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    fun allInfoOkaySelectChange() {
        _scannerUiState.update {
            it.copy(
                infoStatus = InfoStatus.Verified
            )
        }
    }

    fun notAllInfoOkaySelectChange() {
        _scannerUiState.update {
            it.copy(
                infoStatus = InfoStatus.NotVerified
            )
        }
    }

    private suspend fun registerMov(registerMovRequest: RegisterMovRequest): Result<SimpleResponse> {
        return try {
            val resp = recordsRepository.registerMov(registerMovRequest)
            Result.success(resp)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)

        }
    }

    fun onRegisterMov(
    ) {
        if (scannerUiState.value.infoStatus == InfoStatus.Verified) {

            _scannerUiState.update {
                it.copy(
                    isLoading = true
                )
            }
            viewModelScope.launch {
                val authInfo = authRepository.getToken()
                val idAccount = authInfo!!.account
                val movement = when (scannerUiState.value.movement) {
                    Movement.Unknown -> 2
                    Movement.CheckIn -> 1
                    Movement.CheckOut -> 0
                }
                val idAccessCard = scannerUiState.value.accessCard!!.idTarjetaAcceso

                val registerMovRequest = RegisterMovRequest(
                    idCuenta = idAccount.toLong(),
                    movement = movement,
                    idTarjetaAcceso = idAccessCard
                )
                Log.i("Register Mov R", registerMovRequest.toString())

                registerMov(registerMovRequest).onSuccess { resp ->
                    _scannerUiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            message = resp.message
                        )
                    }
                }.onFailure { error ->
                    _scannerUiState.update {
                        it.copy(
                            isLoading = false,
                            isError = true,
                            message = error.message ?: "Error desconocido"
                        )
                    }
                }
            }
        } else {
            _scannerUiState.update {
                it.copy(
                    isError = true,
                    message = "Inconsistencia en los datos!\n Retener y registrar manualmente al usuario"
                )
            }
        }

    }
}