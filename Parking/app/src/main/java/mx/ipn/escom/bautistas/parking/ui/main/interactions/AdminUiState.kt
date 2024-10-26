package mx.ipn.escom.bautistas.parking.ui.main.interactions

import mx.ipn.escom.bautistas.parking.model.AccessCard
import mx.ipn.escom.bautistas.parking.model.AccountFromRes
import mx.ipn.escom.bautistas.parking.model.Incident

data class AdminUiState(
    val accessCardsList: List<AccessCard> = listOf(),
    val accountsList: List<AccountFromRes> = listOf(),
    val incidentsList: List<Incident> = listOf(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
)
