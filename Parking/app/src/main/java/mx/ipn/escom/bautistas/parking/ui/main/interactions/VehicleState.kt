package mx.ipn.escom.bautistas.parking.ui.main.interactions

data class VehicleState(
    val isTypeVehicleSelected: Boolean = false,
    val isPlateValid: Boolean = true,
    val isModelValid: Boolean = true,
    val isBrandValid: Boolean = true,
    val isColorValid: Boolean = true,
    val isDocumentTaken: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = ""
)
