package mx.ipn.escom.bautistas.parking.ui.main.interactions

import mx.ipn.escom.bautistas.parking.model.AccessCard

data class UEUiState(
    val accessCards: List<AccessCard> = listOf(),
//    val alterDrivers:List<AccessCard> = listOf(),
    val hashCard: String = "",
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
)
