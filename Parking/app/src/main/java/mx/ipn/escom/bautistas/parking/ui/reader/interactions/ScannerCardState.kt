package mx.ipn.escom.bautistas.parking.ui.reader.interactions

import mx.ipn.escom.bautistas.parking.model.AccessCard

data class ScannerCardState(
    val accessCard: AccessCard? = null,
    val movement: Int = 0,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
)
