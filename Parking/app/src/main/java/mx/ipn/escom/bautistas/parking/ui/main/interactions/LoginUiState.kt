package mx.ipn.escom.bautistas.parking.ui.main.interactions


data class LoginUiState(
    val isEmailValid: Boolean = true,
    val isVisiblePassword: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
)
