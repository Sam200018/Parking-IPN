package mx.ipn.escom.bautistas.parking.ui.main.interactions

import mx.ipn.escom.bautistas.parking.model.AccessCard

data class AdminUiState(
    val accessCardsList: List<AccessCard> = listOf(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
)
