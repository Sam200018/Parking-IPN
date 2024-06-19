package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import mx.ipn.escom.bautistas.parking.data.card.AccessCardRepository
import mx.ipn.escom.bautistas.parking.model.AccessCard
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AdminUiState
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val accessCardRepository: AccessCardRepository,

    ) : ViewModel() {

    private val _adminUiState = MutableStateFlow(AdminUiState())
    val adminUiState = _adminUiState

    init {
        connectPusher()
    }

    private fun connectPusher() {
        accessCardRepository.getAccessCardsConnect(
            "accessCards",
            "App\\Events\\AccessCardCreated"
        ) {
            Log.i("Pusher X", "Received event with data: $it")
            val accessCard = parseAccessCard(it)
            Log.i("Pusher X", "Received event with data transformed: $accessCard")
        }

    }

    private fun parseAccessCard(data: String): List<AccessCard> {
        val gson = Gson()
        val type = object : TypeToken<Map<String, List<AccessCard>>>() {}.type
        val accessCardsMap: Map<String, List<AccessCard>> = gson.fromJson(data, type)
        return accessCardsMap["accessCards"] ?: emptyList()
    }

    override fun onCleared() {
        super.onCleared()
        accessCardRepository.disconnect()
    }
}