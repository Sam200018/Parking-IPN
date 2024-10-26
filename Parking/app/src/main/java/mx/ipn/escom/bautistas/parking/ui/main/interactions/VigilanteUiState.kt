package mx.ipn.escom.bautistas.parking.ui.main.interactions

import mx.ipn.escom.bautistas.parking.model.AccessControlTransaction
import mx.ipn.escom.bautistas.parking.model.Incident

data class VigilanteUiState(
    val recordList: List<AccessControlTransaction> = listOf(),
    val incidentList: List<Incident> = listOf(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
)
