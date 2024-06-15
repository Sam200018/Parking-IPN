package mx.ipn.escom.bautistas.parking.ui.main.interactions

import mx.ipn.escom.bautistas.parking.model.AccessCard
import mx.ipn.escom.bautistas.parking.model.Registro

data class VigilanteUiState(
    val recordList: List<Registro> = listOf(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
)
