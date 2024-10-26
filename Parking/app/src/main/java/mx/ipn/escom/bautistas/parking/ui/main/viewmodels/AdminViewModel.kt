package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.card.AccessCardRepository
import mx.ipn.escom.bautistas.parking.data.incidents.IncidentsRepository
import mx.ipn.escom.bautistas.parking.data.user.UserRepository
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AdminUiState
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val accessCardRepository: AccessCardRepository,
    private val incidentsRepository: IncidentsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _adminUiState = MutableStateFlow(AdminUiState())
    val adminUiState = _adminUiState


    init {
        getAllAccessCards()
        getAllIncidents()
        getAllAccounts()
    }

    private fun getAllAccounts() {
        viewModelScope.launch {
            val resp = userRepository.getAllAccounts()
            _adminUiState.update {
                it.copy(accountsList = resp.accounts, isLoading = false)
            }

        }
    }

    private fun getAllIncidents() {
        viewModelScope.launch {
            val resp = incidentsRepository.getAllIncidents()
            _adminUiState.update {
                it.copy(incidentsList = resp.incidentes)
            }
        }
    }


    private fun getAllAccessCards() {
        _adminUiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val resp = accessCardRepository.getAllCards(null)
            _adminUiState.update {
                it.copy(accessCardsList = resp.accessCards)
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        accessCardRepository.disconnect()
    }
}