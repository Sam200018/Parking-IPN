package mx.ipn.escom.bautistas.parking.ui.main.interactions

import mx.ipn.escom.bautistas.parking.model.Cuenta


enum class AuthStatus {
    Checking,
    Authenticated,
    Unauthenticated
}

data class AuthState(
    val authStatus: AuthStatus = AuthStatus.Checking,
    val message: String? = null,
    val token: String? = null,
    val account: Cuenta? = null,
)
