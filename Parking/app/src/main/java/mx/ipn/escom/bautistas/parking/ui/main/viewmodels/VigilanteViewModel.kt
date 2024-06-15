package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.records.RecordsRepository
import mx.ipn.escom.bautistas.parking.model.Registro
import mx.ipn.escom.bautistas.parking.ui.main.interactions.VigilanteUiState
import javax.inject.Inject

@HiltViewModel
class VigilanteViewModel @Inject constructor(
    private val recordsRepository: RecordsRepository
) : ViewModel() {

    private val _vigilanteUiState = MutableStateFlow(VigilanteUiState())
    val vigilanteUiState = _vigilanteUiState

    init {
        connectPusher()

    }

    private fun connectPusher() {
        recordsRepository.getRecordsConnect(
            "records",
            "App\\Events\\MoveRegistered"
        ) {
            Log.i("Pusher X", "Received event with data: $it")
            val records = parseRecords(it)
            Log.i("Pusher X", "Received event with data transformed: $records")
            _vigilanteUiState.update {
                VigilanteUiState(recordList = records)
            }
        }
        recordsRepository.onConnected {
            _vigilanteUiState.update {
                it.copy(isLoading = true)
            }
            getAllRecords()
        }
    }

    private fun parseRecords(data: String): List<Registro> {
        val gson = Gson()
        val type = object : TypeToken<Map<String, List<Registro>>>() {}.type
        val recordsMap: Map<String, List<Registro>> = gson.fromJson(data, type)
        return recordsMap["records"] ?: emptyList()
    }

    override fun onCleared() {
        super.onCleared()
        recordsRepository.disconnect()
    }

    private fun getAllRecords() {
        viewModelScope.launch {
            val resp = recordsRepository.getAllRecords()
            Log.i("Ask for resp", resp.message)
        }
    }
}