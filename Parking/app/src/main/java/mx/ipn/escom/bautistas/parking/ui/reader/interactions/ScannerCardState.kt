package mx.ipn.escom.bautistas.parking.ui.reader.interactions

import mx.ipn.escom.bautistas.parking.model.AccessCard

enum class InfoStatus {
    Unverified,
    Verified,
    NotVerified,
}

enum class Movement{
    Unknown,
    CheckIn,
    CheckOut,
}

data class ScannerCardState(
    val accessCard: AccessCard? = null,
    val infoStatus: InfoStatus = InfoStatus.Unverified,
    val movement: Movement = Movement.Unknown,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
)
