package mx.ipn.escom.bautistas.parking.ui.reader.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.card.AccessCardDataSource
import mx.ipn.escom.bautistas.parking.data.card.AccessCardRepository
import mx.ipn.escom.bautistas.parking.model.AccessCard
import mx.ipn.escom.bautistas.parking.model.CardInfoTokeResponse
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.ScannerCardState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val accessCardRepository: AccessCardRepository
) : ViewModel() {

    val _scannerUiState = MutableStateFlow(ScannerCardState())
    val scannerUiState = _scannerUiState.asStateFlow()

    fun scannerCardToken(cardToken: String) {
        _scannerUiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            getCardInfoByToken(cardToken).onSuccess { resp ->
                Log.i("CardToken", resp.accessCard.toString())

                _scannerUiState.update {
                    ScannerCardState(
                        accessCard = resp.accessCard
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
}