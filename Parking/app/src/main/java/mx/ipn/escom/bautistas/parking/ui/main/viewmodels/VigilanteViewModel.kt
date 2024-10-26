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
import mx.ipn.escom.bautistas.parking.data.incidents.IncidentsRepository
import mx.ipn.escom.bautistas.parking.data.records.RecordsRepository
import mx.ipn.escom.bautistas.parking.model.Incident
import mx.ipn.escom.bautistas.parking.ui.main.interactions.VigilanteUiState
import javax.inject.Inject

@HiltViewModel
class VigilanteViewModel @Inject constructor(
    private val recordsRepository: RecordsRepository,
    private val incidentsRepository: IncidentsRepository
) : ViewModel() {

    private val _vigilanteUiState = MutableStateFlow(VigilanteUiState())
    val vigilanteUiState = _vigilanteUiState

    init {
        connectPusher()
        connectPusherIncidents()
    }

    private fun connectPusherIncidents() {
        incidentsRepository.getIncidentsConnect(
            "incidents",
            "App\\Events\\IncidentCreated"
        ) {
            val incidents = parseIncidents(it)

            _vigilanteUiState.update {

                it.copy(incidentList = incidents, isLoading = false)
            }

        }

        incidentsRepository.onConnected {
            _vigilanteUiState.update {
                it.copy(isLoading = true)
            }
            getAllIncidents()
        }

    }

    private fun getAllIncidents() {
        viewModelScope.launch {
            val resp = incidentsRepository.getIncidentsSync()
            Log.i("Ask for resp", resp.message)
        }
    }

    private fun parseIncidents(data: String): List<Incident> {
        val gson = Gson()
        val type = object : TypeToken<Map<String, List<Incident>>>() {}.type
        val incidentsMap: Map<String, List<Incident>> = gson.fromJson(data, type)
        return incidentsMap["incidents"] ?: emptyList()
    }

    private fun connectPusher() {
        recordsRepository.getRecordsConnect(
            "records",
            "App\\Events\\MoveRegistered"
        ) {
            Log.i("Pusher X", "Received event with data: $it")
//            val records = parseRecords(it)
//            Log.i("Pusher X", "Received event with data transformed: $records")
//            _vigilanteUiState.update {
//                it.copy(recordList = records, isLoading = false)
//            }

            getAllRecords()

        }
        recordsRepository.onConnected {
            _vigilanteUiState.update {
                it.copy(isLoading = true)
            }
            getAllRecordsSync()
        }
    }

    private fun getAllRecords() {
        _vigilanteUiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val resp = recordsRepository.getAllRecords()
            _vigilanteUiState.update {
                it.copy(recordList = resp.transactions, isLoading = false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        recordsRepository.disconnect()
        incidentsRepository.disconnect()
    }

    private fun getAllRecordsSync() {
        viewModelScope.launch {
            val resp = recordsRepository.getAllRecordsSync()
            Log.i("Ask for resp", resp.message)
        }
    }
}