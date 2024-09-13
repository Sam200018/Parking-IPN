package mx.ipn.escom.bautistas.parking.ui.main.interactions

data class NewUserState(
    val isNameValid: Boolean = true,
    val isPLastNameValid: Boolean = true,
    val isMLastNameValid: Boolean = true,
    val isPhoneValid: Boolean = true,
    val isEmailValid: Boolean = true,
    val isPersonPhotoTaken: Boolean = false,
    val isIdentificationPhotoTaken: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message:String = "",
)