package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

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
import mx.ipn.escom.bautistas.parking.model.AccessCardResponse
import mx.ipn.escom.bautistas.parking.ui.main.interactions.UEUiState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UEViewModel @Inject constructor(
    private val accessCardRepository: AccessCardRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {


    private val _UEUiState = MutableStateFlow(UEUiState())
    val ueUiState = _UEUiState.asStateFlow()

    init {
        _UEUiState.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            val userInfo = authRepository.getToken()
            Log.i("User", userInfo.toString())

            val hasCard = accessCardRepository.getCardHash()
            _UEUiState.update {
                it.copy(
                    hashCard = hasCard ?: ""
                )
            }

            getAccessCardByUser(userInfo!!.account).onSuccess { accessCardResp ->

                Log.i("accessCard", accessCardResp.accessCards.toString())
                _UEUiState.update {
                    it.copy(
                        accessCards = accessCardResp.accessCards
                    )
                }
            }.onFailure { error ->
                _UEUiState.update {
                    UEUiState(
                        isError = true,
                        message = error.message ?: "Error desconocido"
                    )
                }
            }
        }
    }


    private suspend fun getAccessCardByUser(idAccount: Long): Result<AccessCardResponse> {
        return try {
            val resp = accessCardRepository.getAllCards(idAccount)
            Result.success(resp)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    fun setCardHashAsDefault(hashCard: String) {
        accessCardRepository.saveHash(hashCard)
        _UEUiState.update {
            it.copy(
                hashCard = hashCard
            )
        }
    }

}